package org.shoestore.product.usecase;

import java.util.List;
import org.shoestore.product.model.Product;
import org.shoestore.product.repository.ProductReader;

public class ProductUseCase {

    private final ProductReader productReader;

    public ProductUseCase(ProductReader productReader) {
        this.productReader = productReader;
    }

    public List<Product> getProductsByProductIds(List<Long> productIds) {
        return this.productReader.getProductsByIds(productIds);
    }
}
