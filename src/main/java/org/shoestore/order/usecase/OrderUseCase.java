package org.shoestore.order.usecase;

import java.time.LocalDateTime;
import java.util.List;
import org.shoestore.Util;
import org.shoestore.service.dto.StockDiscountRequestDto;
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
    public Order purchase(List<Product> products, User user) {
        Order order = new Order(products, user);
        return orderWriter.saveOrder(order);
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

    /**
     * 주문 실패 보상 로직
     */
    public void purchaseFailure(Order order) {
        orderWriter.deleteOrder(order.getOrderId());
    }

    /**
     * 주문 취소 실패 보상 로직
     */
    public void cancelFailure(Order order) {
        orderWriter.updateOrderCancelFailure(order.getOrderId());
    }

    /**
     * 주문 조회
     */
    public Order getOrderById(Long orderId) {
        return orderReader.getOrderById(orderId);
    }

    /**
     * 주문 부분취소 보상로직
     */
    public void partialCancelFailure(Order order, Product product) {
        order.undoPartialCancel(product.getProductId());
        orderWriter.updateOrder(order);
    }

    public double getStockDiscountAmount(StockDiscountRequestDto req) {
        LocalDateTime startDateTime = Util.milliToLocalDateTime(req.startTimestamp);
        LocalDateTime endDateTime = Util.milliToLocalDateTime(req.endTimestamp);
        List<Order> stockDiscountedOrder = orderReader.getStockDiscountedOrder(startDateTime, endDateTime);
        return stockDiscountedOrder.stream().mapToDouble(Order::getStockDiscountSaleAmount).sum();
    }
}
