package org.shoestore.repository;

import org.shoestore.domain.model.order.Order;

public interface OrderWriter {

    void saveOrder(Order order);

    void updateOrder(Order order);
}
