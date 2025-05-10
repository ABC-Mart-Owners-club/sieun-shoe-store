package org.shoestore.product.repository;

import java.util.List;
import org.shoestore.product.model.Product;

public interface ProductReader {

    List<Product> getProductsByIds(List<Long> productIds);

    Product getProductById(Long productId);
}
