package org.shoestore.cart.model;

import java.util.ArrayList;
import java.util.List;
import org.shoestore.cart.model.vo.CartItem;
import org.shoestore.product.model.Product;

public class Cart {

    private final Long cartId;
    private final List<CartItem> cartItems; // 상품 목록

    // region constructor
    public Cart(){
        this.cartId = null;
        this.cartItems = new ArrayList<>();
    }
    public Cart(List<Product> products) {
        this.cartId = null;
        this.cartItems = products.stream().map(CartItem::new).toList();
    }
    // endregion

    // region getter logic

    /**
     * 장바구니 상품들 productId 조회
     */
    public List<Long> getProductIds() {
        return this.cartItems.stream().map(CartItem::productId).toList();
    }
    // endregion

    //region setter logic

    /**
     * 장바구니 추가
     */
    public void addProduct(Product product) {
        CartItem cartItem = new CartItem(product);
        if (this.cartItems.stream().anyMatch(p -> p.equals(cartItem))) {
            throw new RuntimeException("중복 상품은 담을 수 없음");
        }
        this.cartItems.add(cartItem);
    }
    // endregion
}
