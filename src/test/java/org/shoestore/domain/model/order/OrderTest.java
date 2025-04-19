package org.shoestore.domain.model.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shoestore.domain.model.User.User;
import org.shoestore.domain.model.cart.Cart;
import org.shoestore.domain.model.product.Product;

class OrderTest {

    private Cart cart;
    private final User user = new User("시은", "0104590854");
    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    void init(){
        cart = new Cart();
        product1 = new Product("조던", "나이키", 19000);
        product2 = new Product("프레데터", "아디다스", 30000);
        product3 = new Product("에어포스", "나이키", 13000);
        cart.addProduct(product1);
        cart.addProduct(product2);
        cart.addProduct(product3);
    }

    @Test
    void cancel_주문취소_테스트() {
        Order order = new Order(cart, user);
        order.cancel();
        assertThat(order.isCanceled()).isEqualTo(true);
        assertThat(order.isPartialCanceled()).isEqualTo(false);
    }

    @Test
    void partialCancel_주문_부분취소_테스트() {
        Order order = new Order(cart, user);
        order.partialCancel(product1);
        assertThat(order.isPartialCanceled()).isEqualTo(true);
        assertThat(order.isCanceled()).isEqualTo(false);
    }

    @Test
    void partialCancel_주문_부분취소_테스트_상품불일치() {
        Product notPurchasedProduct = new Product("이지부스트", "아디다스", 339293);
        Order order = new Order(cart, user);
        assertThatThrownBy(() -> order.partialCancel(notPurchasedProduct))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("동일 상품을 찾을 수 없음");
    }
}