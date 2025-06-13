package org.shoestore.order.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.shoestore.order.model.Order;
import org.shoestore.product.model.Product;

public interface OrderReader {

    List<Order> getOrdersHavingProduct(Long productId);

    Order getOrderById(Long orderId);

    List<Order> getStockDiscountedOrder(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
