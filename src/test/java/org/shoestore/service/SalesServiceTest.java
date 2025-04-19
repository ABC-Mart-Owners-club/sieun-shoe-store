package org.shoestore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.shoestore.domain.model.User.User;
import org.shoestore.domain.model.cart.Cart;
import org.shoestore.domain.model.order.Order;
import org.shoestore.domain.model.product.Product;
import org.shoestore.repository.OrderReader;
import org.shoestore.repository.OrderWriter;

class SalesServiceTest {

    private final SalesService salesService = new SalesService(initOrderReader(), initOrderWriter());
    private final Product targetProduct = new Product("에어포스", "나이키", 0);
    @Test
    void purchase() {
    }

    @Test
    void cancel() {
    }

    @Test
    void partialCancel() {
    }

    @Test
    void getProductSalesAmount() {
        double productSalesAmount = salesService.getProductSalesAmount(targetProduct);
        assertThat(productSalesAmount).isEqualTo(40680);

    }

    private OrderWriter initOrderWriter(){
        return new OrderWriter() {
            @Override
            public void saveOrder(Order order) {
                System.out.println("주문 저장!");
            }

            @Override
            public void updateOrder(Order order) {
                System.out.println("주문 업데이트!");
            }
        };
    }

    private OrderReader initOrderReader(){

        Cart cart1 = new Cart();
        cart1.addProduct(new Product("에어포스", "나이키", 19900));

        Cart cart2 = new Cart();
        cart2.addProduct(new Product("에어포스", "나이키", 1900));

        Cart cart3 = new Cart();
        cart3.addProduct(new Product("에어포스", "나이키", 18880));

        User me = new User("시은", "3939");
        return new OrderReader() {
            @Override
            public List<Order> getOrdersHavingProduct(Product product) {
                List<Order> orders = new ArrayList<>();
                orders.add(new Order(cart1, me));
                orders.add(new Order(cart2, me));
                orders.add(new Order(cart3, me));
                return orders;
            }
        };
    }
}