package org.shoestore.domain.model.cart;

import java.util.ArrayList;
import java.util.List;
import org.shoestore.domain.model.product.Product;

public class Cart {

    private final List<Product> products; // 상품 목록

    // region constructor
    public Cart(){
        this.products = new ArrayList<>();
    }
    public Cart(List<Product> products) {
        this.products = products;
    }
    // endregion

    // region getter logic
    public List<Product> getProducts() {
        return this.products;
    }
    // endregion

    //region setter logic
    public void addProduct(Product product) {
        if (this.products.stream().anyMatch(p -> p.equals(product))) {
            throw new RuntimeException("중복 상품은 담을 수 없음");
        }
        this.products.add(product);
    }
    // endregion
}
