package org.shoestore.order.repository;

import org.shoestore.order.model.Order;

public interface OrderWriter {

    void saveOrder(Order order);

    void updateOrder(Order order);
}
