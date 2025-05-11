package org.shoestore.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shoestore.user.model.User;
import org.shoestore.product.model.Product;
import org.shoestore.order.model.Order;

class OrderTest {

    private final User user = new User("시은", "0104590854");
    List<Product> products;
    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    void init(){
        product1 = new Product(1L, "조던", "나이키", 19000, null);
        product2 = new Product(2L, "프레데터", "아디다스", 30000, null);
        product3 = new Product(3L,"에어포스", "나이키", 13000, null);
        products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
    }

    @Test
    void cancel_주문취소_테스트() {
        Order order = new Order(products, user);
        order.cancel();
        assertThat(order.isCanceled()).isEqualTo(true);
        assertThat(order.isPartialCanceled()).isEqualTo(false);
    }

    @Test
    void partialCancel_주문_부분취소_테스트() {
        Order order = new Order(products, user);
        order.partialCancel(product1.getProductId());
        assertThat(order.isPartialCanceled()).isEqualTo(true);
        assertThat(order.isCanceled()).isEqualTo(false);
    }

    @Test
    void partialCancel_주문_부분취소_테스트_상품불일치() {
        Product notPurchasedProduct = new Product(4L, "이지부스트", "아디다스", 339293, null);
        Order order = new Order(products, user);
        assertThatThrownBy(() -> order.partialCancel(notPurchasedProduct.getProductId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("동일 상품을 찾을 수 없음");
    }
}