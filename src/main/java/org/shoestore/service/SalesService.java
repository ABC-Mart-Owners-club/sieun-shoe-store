package org.shoestore.service;

import java.util.ArrayList;
import java.util.List;
import org.shoestore.payment.model.Payment;
import org.shoestore.payment.model.PaymentFactory;
import org.shoestore.payment.model.type.CardType;
import org.shoestore.payment.model.type.PaymentMethod;
import org.shoestore.payment.model.vo.PaymentInfo;
import org.shoestore.payment.usecase.PaymentUseCase;
import org.shoestore.service.dto.PurchaseRequestDto;
import org.shoestore.user.model.User;
import org.shoestore.order.model.Order;
import org.shoestore.order.usecase.OrderUseCase;
import org.shoestore.product.model.Product;
import org.shoestore.product.usecase.ProductUseCase;

public class SalesService {

    private final OrderUseCase orderUseCase;
    private final ProductUseCase productUseCase;
    private final PaymentUseCase paymentUseCase;

    public SalesService(OrderUseCase orderUseCase,
            ProductUseCase productUseCase,
            PaymentUseCase paymentUseCase
    ) {
        this.orderUseCase = orderUseCase;
        this.productUseCase = productUseCase;
        this.paymentUseCase = paymentUseCase;
    }

    /**
     * 구매
     * <p>1. Product 조회</p>
     * <p>2. 조회된 상품 기준 주문 저장</p>
     * <p>3. 결제 생성 및 저장</p>
     * <p>4. 상품 판매 처리</p>
     */
    public void purchase(PurchaseRequestDto dto) {

        Long now = System.currentTimeMillis();
        User user = new User(dto.getUserId(), dto.getName(), dto.getPhoneNumber());
        Order order = null;
        List<Product> products = new ArrayList<>();
        List<Payment> payments = new ArrayList<>();

        try {
            // 1 Product 조회
            products = productUseCase.getProductsByProductIds(dto.getProductIds());
            // 2 조회된 상품 기준 주문 저장
            order = orderUseCase.purchase(products, user);

            // 3 결제 생성 및 저장
            if (dto.getCashAmount() != null) {
                payments.add(PaymentFactory.createPayment(PaymentMethod.CASH,
                        new PaymentInfo(order.getOrderId(), dto.getCashAmount(), now), null));
            }
            if (dto.getCardAmount() != null) {
                payments.add(PaymentFactory.createPayment(PaymentMethod.CREDIT_CARD,
                        new PaymentInfo(order.getOrderId(), dto.cardAmount, now),
                        dto.getCardType()));
            }
            paymentUseCase.pay(order, payments);
            // 4. 상품 판매 처리
            productUseCase.sale(order, products);
        } catch (Exception e) {
            // orderId를 트랜잭션키로 활용하여 update 된 내역 발생 시 롤백 처리
            if (order != null) {
                paymentUseCase.payFailure(order);
                orderUseCase.purchaseFailure(order);
                productUseCase.saleFailure(order);
            }
            throw new RuntimeException("결제 실패 : " + e.getMessage(), e);
        }
    }

    /**
     * 취소
     */
    public void cancel(Long orderId) {
        Order order = orderUseCase.getOrderById(orderId);
        try {
            orderUseCase.cancel(order);
            paymentUseCase.cancel(order);
            productUseCase.cancel(order);
        } catch (Exception e) {
            orderUseCase.cancelFailure(order);
            paymentUseCase.cancelFailure(order);
            productUseCase.cancelFailure(order);
            throw new RuntimeException(e);
        }
    }

    /**
     * 부분취소
     */
    public void partialCancel(Long orderId, Long productId) {
        Order order = orderUseCase.getOrderById(orderId);
        Product product = productUseCase.getProductById(productId);
        try {
            orderUseCase.partialCancel(order, product);
            paymentUseCase.partialCancel(order, product);
            productUseCase.partialCancel(order, product);
        } catch (Exception e) {
            orderUseCase.partialCancelFailure(order, product);
            paymentUseCase.partialCancelFailure(order, product);
            productUseCase.partialCancelFailure(order, product);
            throw new RuntimeException(e);
        }
    }

    /**
     * 상품별 판매금액 조회
     */
    public double getProductSalesAmount(Product product) {
        List<Order> orders = orderUseCase.getOrdersHavingProduct(product);
        return orders.stream().mapToDouble(order -> order.getProductSalesAmount(product)).sum();
    }

    /**
     * 카드사별 판매금액 조회
     */
    public double getCardSalesAmount(CardType cardType) {
        return paymentUseCase.getCardSalesAmount(cardType);
    }
}
