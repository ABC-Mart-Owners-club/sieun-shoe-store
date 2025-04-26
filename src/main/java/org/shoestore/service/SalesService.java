package org.shoestore.service;

import java.util.ArrayList;
import java.util.List;
import org.shoestore.payment.model.CashPayment;
import org.shoestore.payment.model.CreditCardPayment;
import org.shoestore.payment.model.Payment;
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
     */
    public void purchase(PurchaseRequestDto dto) {

        Long now = System.currentTimeMillis(); // use as transaction Key
        User user = new User(dto.getUserId(), dto.getName(), dto.getPhoneNumber());
        Long orderId = null;
        List<Product> products = new ArrayList<>();
        List<Payment> payments = new ArrayList<>();

        try {
            // 1 Product 조회
            products = productUseCase.getProductsByProductIds(dto.getProductIds());
            // 2 조회된 상품 기준 주문 저장
            orderId = orderUseCase.purchase(products, user);

            // 3 결제 생성 및 저장
            if (dto.getCashAmount() != null) {
                payments.add(new CashPayment(new PaymentInfo(orderId, dto.getCashAmount(), now)));
            }
            if (dto.getCardAmount() != null) {
                payments.add(new CreditCardPayment(new PaymentInfo(orderId, dto.cardAmount, now),
                        dto.getCardType()));
            }
            paymentUseCase.pay(payments);
        } catch (Exception e) {
            if (orderId != null) {
                paymentUseCase.payFailure(orderId);
                orderUseCase.purchaseFailure(orderId);
            }
            throw new RuntimeException("결제 실패 : " + e.getMessage(), e);
        }
    }

    /**
     * 취소
     */
    public void cancel(Order order) {
        orderUseCase.cancel(order);
    }

    /**
     * 부분취소
     */
    public void partialCancel(Order order, Product product) {
        orderUseCase.partialCancel(order, product);
    }

    /**
     * 상품별 판매금액 조회
     */
    public double getProductSalesAmount(Product product) {
        List<Order> orders = orderUseCase.getOrdersHavingProduct(product);
        return orders.stream().mapToDouble(order -> order.getProductSalesAmount(product)).sum();
    }
}
