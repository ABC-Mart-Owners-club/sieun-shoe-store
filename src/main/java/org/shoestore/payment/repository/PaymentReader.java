package org.shoestore.payment.repository;

import org.shoestore.payment.model.Payment;
import java.util.List;
import org.shoestore.payment.model.type.CardType;

public interface PaymentReader {

    List<Payment> getPaymentsByOrderId(Long orderId);

    double getCardSalesAmount(CardType cardType);
}

