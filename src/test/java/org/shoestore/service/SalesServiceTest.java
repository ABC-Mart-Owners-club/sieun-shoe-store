package org.shoestore.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.shoestore.payment.usecase.PaymentUseCase;
import org.shoestore.user.model.User;
import org.shoestore.order.model.Order;
import org.shoestore.order.usecase.OrderUseCase;
import org.shoestore.product.model.Product;
import org.shoestore.order.repository.OrderReader;
import org.shoestore.order.repository.OrderWriter;

class SalesServiceTest {

    private final SalesService salesService = new SalesService(
            new OrderUseCase(initOrderWriter(), initOrderReader()), null,
            new PaymentUseCase(null, null), null);
    private final Product targetProduct = new Product(1L,"에어포스", "나이키", 0, null);

    @Test
    void getProductSalesAmount() {
        double productSalesAmount = salesService.getProductSalesAmount(targetProduct);
        assertThat(productSalesAmount).isEqualTo(22000);

    }

    private OrderWriter initOrderWriter(){
        return new OrderWriter() {
            @Override
            public Order saveOrder(Order order) {
                System.out.println("주문 저장!");
                return null;
            }

            @Override
            public void updateOrder(Order order) {
                System.out.println("주문 업데이트!");
            }

            @Override
            public void deleteOrder(Long orderId) {
                System.out.println("주문 삭제!");
            }

            @Override
            public void updateOrderCancelFailure(Long canceledOrderId) {
                System.out.println("주문 취소 실패 보상 로직");
            }
        };
    }

    private OrderReader initOrderReader(){

        List<Product> productList1 = new ArrayList<>();
        List<Product> productList2 = new ArrayList<>();
        Product product1 = new Product(3L, "조던", "나이키", 19000, null);
        Product product2 = new Product(2L, "프레데터", "아디다스", 30000, null);
        Product product3 = new Product(1L, "에어포스", "나이키", 13000, null);
        Product product4 = new Product(1L, "에어포스", "나이키", 9000, null);
        productList1.add(product1);
        productList1.add(product3);
        productList2.add(product2);
        productList2.add(product4);

        User me = new User("시은", "3939");
        return new OrderReader() {
            @Override
            public List<Order> getOrdersHavingProduct(Long productId) {
                List<Order> orders = new ArrayList<>();
                orders.add(new Order(productList1, me));
                orders.add(new Order(productList2, me));
                return orders;
            }

            @Override
            public Order getOrderById(Long orderId) {
                return null;
            }
        };
    }

    @Test
    void purchase() {
        // 주문 생성 됐는지
        // payment 생성 됐는지
    }

    @Test
    void purchase_실패() {
        // 주문 생성 취소 됐는지
        // payment 생성 취소 됐는지
    }

    @Test
    void cancel() {
    }

    @Test
    void partialCancel() {
    }

    @Test
    void testGetProductSalesAmount() {
    }

    @Test
    void getCardSalesAmount() {
    }
}