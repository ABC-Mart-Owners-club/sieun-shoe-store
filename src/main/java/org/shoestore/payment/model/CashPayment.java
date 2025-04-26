package org.shoestore.payment.model;

import org.shoestore.payment.model.type.PaymentMethod;
import org.shoestore.payment.model.vo.PaymentInfo;

public class CashPayment extends Payment{

    // region constructor
    public CashPayment(Long paymentId, PaymentInfo paymentInfo) {
        super(paymentId, paymentInfo);
    }

    public CashPayment(PaymentInfo paymentInfo) {
        super(paymentInfo);
    }
    // endregion

    // region getter logic
    @Override
    PaymentMethod getPaymentMethod() {
        return PaymentMethod.CASH;
    }
    // endregion
}
