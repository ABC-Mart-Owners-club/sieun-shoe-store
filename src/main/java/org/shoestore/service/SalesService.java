package org.shoestore.service;

import java.util.List;
import org.shoestore.User.model.User;
import org.shoestore.cart.model.Cart;
import org.shoestore.order.model.Order;
import org.shoestore.order.usecase.OrderUseCase;
import org.shoestore.product.model.Product;
import org.shoestore.product.usecase.ProductUseCase;

public class SalesService {

    private final OrderUseCase orderUseCase;
    private final ProductUseCase productUseCase;

    public SalesService(OrderUseCase orderUseCase, ProductUseCase productUseCase) {
        this.orderUseCase = orderUseCase;
        this.productUseCase = productUseCase;
    }

    /**
     * 구매
     * <p>1. Cart에 담겨진 상품 기준 Product 조회</p>
     * <p>2. 조회된 상품 기준 주문 생성</p>
     */
    public void purchase(Cart cart, User user) {
        List<Product> productsByProductIds = productUseCase.getProductsByProductIds(cart.getProductIds());
        orderUseCase.purchase(productsByProductIds, user);
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
