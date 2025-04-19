package org.shoestore.repository;

import java.util.List;
import org.shoestore.domain.model.order.Order;
import org.shoestore.domain.model.product.Product;

public interface OrderReader {

    List<Order> getOrdersHavingProduct(Product product);
}
