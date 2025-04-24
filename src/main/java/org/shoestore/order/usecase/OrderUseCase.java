package org.shoestore.order.usecase;

import java.util.List;
import org.shoestore.user.model.User;
import org.shoestore.order.model.Order;
import org.shoestore.order.repository.OrderReader;
import org.shoestore.order.repository.OrderWriter;
import org.shoestore.product.model.Product;

public class OrderUseCase {

    private final OrderWriter orderWriter;
    private final OrderReader orderReader;

    public OrderUseCase(OrderWriter orderWriter, OrderReader orderReader) {
        this.orderWriter = orderWriter;
        this.orderReader = orderReader;
    }

    /**
     * 구매
     */
    public void purchase(List<Product> products, User user) {
        Order order = new Order(products, user);
        orderWriter.saveOrder(order);
    }

    /**
     * 주문 취소
     */
    public void cancel(Order order) {
        order.cancel();
        orderWriter.updateOrder(order);
    }

    /**
     * 주문 부분취소 (상품 취소)
     */
    public void partialCancel(Order order, Product product) {
        order.partialCancel(product.getProductId());
        orderWriter.updateOrder(order);
    }

    /**
     * 상품이 포함된 주문 목록 조회
     */
    public List<Order> getOrdersHavingProduct(Product product) {
        return orderReader.getOrdersHavingProduct(product.getProductId());
    }
}
