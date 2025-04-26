package org.shoestore.payment.model;

import org.shoestore.payment.model.type.PaymentMethod;
import org.shoestore.payment.model.vo.PaymentInfo;

public abstract class Payment {

    protected final Long paymentId;
    protected final PaymentInfo paymentInfo;

    // region constructor
    public Payment(Long paymentId, PaymentInfo paymentInfo) {
        this.paymentId = paymentId;
        this.paymentInfo = paymentInfo;
    }

    public Payment(PaymentInfo paymentInfo) {
        this.paymentId = null;
        this.paymentInfo = paymentInfo;
    }

    // endregion

    // region getter logic
    abstract PaymentMethod getPaymentMethod();
    // endregion
}
