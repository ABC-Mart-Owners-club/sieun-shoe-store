package org.shoestore.payment.usecase;

import java.util.List;
import org.shoestore.payment.model.Payment;
import org.shoestore.payment.repository.PaymentReader;
import org.shoestore.payment.repository.PaymentWriter;

public class PaymentUseCase {

    private final PaymentWriter paymentWriter;
    private final PaymentReader paymentReader;

    public PaymentUseCase(PaymentWriter paymentWriter, PaymentReader paymentReader) {
        this.paymentWriter = paymentWriter;
        this.paymentReader = paymentReader;
    }

    /**
     * 결제
     */
    public void pay(List<Payment> payments) {
        paymentWriter.savePayments(payments);
    }

    /**
     * 결제 실패 보상로직
     */
    public void payFailure(Long orderId) {
        List<Payment> payments =  paymentReader.getPaymentsByOrderId(orderId);
        if (payments.isEmpty()) {
            return;
        }
        paymentWriter.deletePaymentsByOrderId(orderId);
    }
}
