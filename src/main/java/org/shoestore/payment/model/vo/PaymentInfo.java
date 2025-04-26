package org.shoestore.payment.model.vo;

public record PaymentInfo(
        Long orderId,
        double amount,
        Long paymentTimestamp
) {

}
