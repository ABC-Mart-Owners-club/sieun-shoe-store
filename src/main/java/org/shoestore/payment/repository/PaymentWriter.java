package org.shoestore.payment.repository;

import java.util.List;
import org.shoestore.payment.model.Payment;

public interface PaymentWriter {

    void savePayments(List<Payment> payments);

    void deletePaymentsByOrderId(Long orderId);

    void updatePaymentCancel(Long canceledOrderId);

    void updatePaymentCancelFailure(Long canceledOrderId);
}
