package org.shoestore.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.shoestore.product.model.Product;

class ProductTest {

    @Test
    void 객체_동등성_테스트(){
        Product nikeSale = new Product("조던", "nike", 1000);
        Product nike = new Product("조던", "nike", 10000);
        assertThat(nike).isEqualTo(nikeSale);
    }
}