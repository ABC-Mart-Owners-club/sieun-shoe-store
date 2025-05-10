package org.shoestore.order.repository;

import org.shoestore.order.model.Order;

public interface OrderWriter {

    Order saveOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(Long orderId);

    void updateOrderCancelFailure(Long canceledOrderId);
}
