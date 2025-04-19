package org.shoestore.service;

import java.util.List;
import org.shoestore.domain.model.User.User;
import org.shoestore.domain.model.cart.Cart;
import org.shoestore.domain.model.order.Order;
import org.shoestore.domain.model.product.Product;
import org.shoestore.repository.OrderReader;
import org.shoestore.repository.OrderWriter;

public class SalesService {

    private final OrderReader orderReader;
    private final OrderWriter orderWriter;

    public SalesService(OrderReader orderReader, OrderWriter orderWriter) {
        this.orderReader = orderReader;
        this.orderWriter = orderWriter;
    }

    /**
     * 구매
     */
    public void purchase(Cart cart, User user) {
        Order order = new Order(cart, user);
        orderWriter.saveOrder(order);
    }

    /**
     * 취소
     */
    public void cancel(Order order) {
        order.cancel();
        orderWriter.updateOrder(order);
    }

    /**
     * 부분취소
     */
    public void partialCancel(Order order, Product product) {
        order.partialCancel(product);
        orderWriter.updateOrder(order);
    }

    /**
     * 상품별 판매금액 조회
     */
    public double getProductSalesAmount(Product product) {
        List<Order> orders = orderReader.getOrdersHavingProduct(product);
        return orders.stream().mapToDouble(order -> order.getProductSalesAmount(product)).sum();
    }
}
