package org.shoestore.payment.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;
import org.shoestore.order.model.Order;
import org.shoestore.payment.model.type.CardType;
import org.shoestore.payment.model.type.PaymentMethod;
import org.shoestore.payment.model.vo.PaymentInfo;
import org.shoestore.promotion.model.Promotion;

public class Payments {

    private final List<Payment> payments;

    // region constructor
    public Payments(List<Payment> payments) {
        this.payments = payments;
    }

    public Payments(Order order, Double cashAmount, Double cardAmount, CardType cardType, Promotion promotion) {
        Long now = System.currentTimeMillis();

        List<Payment> payments = new ArrayList<>();
        double discountAmount = promotion.getDiscountAmount(order.getTotalPrice());
        if (cashAmount != null) {
            double cashPaymentAmount = Math.max(0, cashAmount - discountAmount);
            discountAmount = Math.max(0, discountAmount - cashAmount);
            payments.add(PaymentFactory.createPayment(PaymentMethod.CASH,
                    new PaymentInfo(order.getOrderId(), cashPaymentAmount, now), null));
        }
        if (cardAmount != null) {
            double cardPaymentAmount = Math.max(0, cardAmount - discountAmount);
            payments.add(PaymentFactory.createPayment(PaymentMethod.CREDIT_CARD,
                    new PaymentInfo(order.getOrderId(), cardPaymentAmount, now), cardType));
        }

        this.payments = payments;
    }
    // endregion

    // region getter logic
    public List<Payment> getPayments() {
        return this.payments;
    }
    // endregion

    // region setter logic

    // endregion
}
