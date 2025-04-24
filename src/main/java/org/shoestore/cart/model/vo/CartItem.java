package org.shoestore.cart.model.vo;

import org.shoestore.product.model.Product;

public record CartItem(

        Long productId
) {

    public CartItem(Product product) {
        this(product.getProductId());
    }
}
