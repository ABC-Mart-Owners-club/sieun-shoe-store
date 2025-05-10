package org.shoestore.product.usecase;

import java.util.List;
import org.shoestore.product.model.Product;
import org.shoestore.product.repository.ProductReader;

public class ProductUseCase {

    private final ProductReader productReader;

    public ProductUseCase(ProductReader productReader) {
        this.productReader = productReader;
    }

    /**
     * 상품 목록 조회
     */
    public List<Product> getProductsByProductIds(List<Long> productIds) {
        return this.productReader.getProductsByIds(productIds);
    }

    /**
     * 상품 조회
     */
    public Product getProductById(Long productId) {
        return this.productReader.getProductById(productId);
    }
}
