package org.shoestore.testprep;

import java.util.ArrayList;
import java.util.List;
import org.shoestore.order.model.Order;
import org.shoestore.order.model.OrderLine;
import org.shoestore.order.model.vo.Buyer;
import org.shoestore.order.model.vo.OrderItem;
import org.shoestore.payment.model.CashPayment;
import org.shoestore.payment.model.CreditCardPayment;
import org.shoestore.payment.model.Payment;
import org.shoestore.payment.model.type.CardType;
import org.shoestore.payment.model.vo.PaymentInfo;
import org.shoestore.product.model.Product;
import org.shoestore.user.model.User;

public class TestDomainModelPrep {
    public final User sieun; // userId : 1
    public final User bumro; // userId : 2
    public final Product productNikeJordan; // productId : 1
    public final Product productNikeAirforce; // productId : 2
    public final Product productAdidasPredator; // productId : 3
    public final Product productPumaNitro; // productId : 4
    public final Order orderPurchased; // orderId : 1 , totalPrice : 550000
    public final Order orderCanceled; // orderId : 2
    public final Order orderPartialCanceled; // orderId : 3
    private static Long paymentIdIncrement;

    /**
     * 테스트용 도메인 모델
     */
    public TestDomainModelPrep() {
        this.sieun = new User(1L, "시은", "0104590854");
        this.bumro = new User(2L, "범로", "0102583752");
        this.productNikeJordan = new Product(1L, "나이키", "조던", 150000, null);
        this.productNikeAirforce = new Product(2L, "나이키", "에어포스", 60000, null);
        this.productAdidasPredator = new Product(3L, "프레데터", "아디다스", 130000, null);
        this.productPumaNitro = new Product(4L, "나이트로", "퓨마", 210000, null);
        this.orderPurchased = new Order(1L, initPurchasedOrderLines(), new Buyer(this.sieun));
        this.orderCanceled = new Order(1L, initCanceledOrderLines(), new Buyer(this.sieun));
        this.orderPartialCanceled = new Order(1L, initPartialCanceledOrderLines(), new Buyer(this.sieun));
        paymentIdIncrement = 0L;
    }

    /**
     * 주문번호에 따른 테스트용 Payment 도메인 모델 생성 메서드
     */
    public static Payment createPaymentWithOrder(Long orderId, Double cashAmount, Double cardAmount,
            CardType cardType) {
        Long now = System.currentTimeMillis();
        if (cashAmount != null) {
            return new CashPayment(++paymentIdIncrement, new PaymentInfo(orderId, cashAmount, now));
        }
        if (cardAmount != null) {
            return new CreditCardPayment(++paymentIdIncrement, new PaymentInfo(orderId, cardAmount, now), cardType);
        }
        return null;
    }

    private List<OrderLine> initPurchasedOrderLines() {
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(new OrderLine(1L, new OrderItem(this.productNikeJordan), false));
        orderLines.add(new OrderLine(2L, new OrderItem(this.productNikeAirforce), false));
        orderLines.add(new OrderLine(3L, new OrderItem(this.productAdidasPredator), false));
        orderLines.add(new OrderLine(4L, new OrderItem(this.productPumaNitro), false));
        return orderLines;
    }

    private List<OrderLine> initCanceledOrderLines() {
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(new OrderLine(5L, new OrderItem(this.productNikeJordan), true));
        orderLines.add(new OrderLine(6L, new OrderItem(this.productNikeAirforce), true));
        orderLines.add(new OrderLine(7L, new OrderItem(this.productAdidasPredator), true));
        orderLines.add(new OrderLine(8L, new OrderItem(this.productPumaNitro), true));
        return orderLines;
    }

    private List<OrderLine> initPartialCanceledOrderLines() {
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(new OrderLine(9L, new OrderItem(this.productNikeJordan), false));
        orderLines.add(new OrderLine(10L, new OrderItem(this.productNikeAirforce), true));
        orderLines.add(new OrderLine(11L, new OrderItem(this.productAdidasPredator), false));
        orderLines.add(new OrderLine(12L, new OrderItem(this.productPumaNitro), false));
        return orderLines;
    }
}
