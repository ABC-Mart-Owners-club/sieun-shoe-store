package org.shoestore.payment.model.type;

public enum PaymentMethod {
    CASH,
    CREDIT_CARD;

    public boolean isCash(){
        return this == CASH;
    }

    public boolean isCreditCard(){
        return this == CREDIT_CARD;
    }
}
