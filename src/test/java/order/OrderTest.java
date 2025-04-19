package order;

public class OrderTest {

//    @Test
//    void 주문_생성() {
//
//        OrderWriter orderWriter = initOrderWriter();
//        orderWriter.purchase(new Order(new ArrayList<>()));
//    }
//
//
//    @Test
//    void 주문_취소() {
//        OrderWriter orderWriter = initOrderWriter();
//        orderWriter.cancel(new Order(new ArrayList<>()));
//    }
//
//    @Test
//    void 주문_부분취소() {
//        OrderWriter orderWriter = initOrderWriter();
//        Order order = new Order(new ArrayList<>(), new User());
//        Product product = new Product("모델", "브랜드", 9);
//
//        orderWriter.partialCancel(order, product);
//    }
//
//    @Test
//    void 상품별_판매_금액_조회() {
//        OrderReader orderReader = initOrderReader();
//        Double productSalesAmount = orderReader.getProductSalesAmount(new Product("모델", "브랜드", 9));
//
//
//    }
//    private static OrderWriter initOrderWriter() {
//        OrderWriter orderWriter = new OrderWriter((new SalesHistory() {
//
//            @Override
//            public double getProductSalesAmount(Product product) {
//                System.out.println("조회 로직 수행!");
//                return 0;
//            }
//
//            @Override
//            public void saveOrder(Order order) {
//                System.out.println("주문 저장!");
//            }
//
//            @Override
//            public void updateOrder(Order order) {
//                System.out.println("주문 업데이트!");
//            }
//        }));
//        return orderWriter;
//    }
//    private static OrderReader initOrderReader() {
//        OrderReader orderReader = new OrderReader((new SalesHistory() {
//
//            @Override
//            public double getProductSalesAmount(Product product) {
//                System.out.println("조회 로직 수행!");
//                return 0;
//            }
//
//            @Override
//            public void saveOrder(Order order) {
//                System.out.println("주문 저장!");
//            }
//
//            @Override
//            public void updateOrder(Order order) {
//                System.out.println("주문 업데이트!");
//            }
//        }));
//        return orderReader;
//    }
}
