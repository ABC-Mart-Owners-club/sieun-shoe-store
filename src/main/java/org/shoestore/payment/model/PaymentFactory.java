package org.shoestore.payment.model;

import org.shoestore.payment.model.type.CardType;
import org.shoestore.payment.model.type.PaymentMethod;
import org.shoestore.payment.model.vo.PaymentInfo;

public class PaymentFactory {
    public static Payment createPayment(PaymentMethod paymentMethod, PaymentInfo paymentInfo, CardType cardType) {
        return switch (paymentMethod) {
            case PaymentMethod.CASH -> new CashPayment(paymentInfo);
            case PaymentMethod.CREDIT_CARD -> new CreditCardPayment(paymentInfo, cardType);
            default ->
                    throw new IllegalArgumentException("Unknown paymentMethod: " + paymentMethod);
        };
    }
}
