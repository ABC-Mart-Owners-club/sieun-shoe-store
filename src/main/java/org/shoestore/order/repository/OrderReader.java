package org.shoestore.order.repository;

import java.util.List;
import org.shoestore.order.model.Order;
import org.shoestore.product.model.Product;

public interface OrderReader {

    List<Order> getOrdersHavingProduct(Long productId);
}
