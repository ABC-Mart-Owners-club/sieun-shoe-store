package org.shoestore.payment.model;

import org.shoestore.payment.model.type.CardType;
import org.shoestore.payment.model.type.PaymentMethod;
import org.shoestore.payment.model.vo.PaymentInfo;

public class CreditCardPayment extends Payment {
    private final CardType cardType;

    // region constructor
    public CreditCardPayment(Long paymentId, PaymentInfo paymentInfo, CardType cardType) {
        super(paymentId, paymentInfo);
        this.cardType = cardType;
    }

    public CreditCardPayment(PaymentInfo paymentInfo, CardType cardType) {
        super(paymentInfo);
        this.cardType = cardType;
    }
    // endregion

    // region getter logic
    @Override
    PaymentMethod getPaymentMethod() {
        return PaymentMethod.CREDIT_CARD;
    }

    public CardType getCardType() {
        return this.cardType;
    }
    // endregion
}
