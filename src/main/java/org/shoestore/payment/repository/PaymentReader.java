package org.shoestore.payment.repository;

import org.shoestore.payment.model.Payment;
import java.util.List;

public interface PaymentReader {

    List<Payment> getPaymentsByOrderId(Long orderId);
}

