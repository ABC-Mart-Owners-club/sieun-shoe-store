package org.shoestore.payment.model.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PaymentInfoTest {

    private final Long orderId = 1L;
    private final Long orderId2 = 1L;
    private final double amount = 1999;
    private final double amount2 = 1999;
    private final Long timestampA = 11249837L;
    private final Long timestampB = 123433345L;

    @Test
    void paymentInfo_생성자_테스트() {
        PaymentInfo paymentInfo = new PaymentInfo(orderId, amount, timestampA);
        assertThat(paymentInfo.orderId()).isEqualTo(orderId);
        assertThat(paymentInfo.amount()).isEqualTo(amount);
        assertThat(paymentInfo.paymentTimestamp()).isEqualTo(timestampA);
    }

    @Test
    void paymentInfo_eqauls_정상() {
        PaymentInfo paymentInfo1 = new PaymentInfo(orderId, amount, timestampA);
        PaymentInfo paymentInfo2 = new PaymentInfo(orderId2, amount2, timestampA);
        assertThat(paymentInfo1).isEqualTo(paymentInfo2);
    }

    @Test
    void paymentInfo_eqauls_오류() {
        PaymentInfo paymentInfo1 = new PaymentInfo(orderId, amount, timestampA);
        PaymentInfo paymentInfo2 = new PaymentInfo(orderId2, amount2, timestampB);
        assertThat(paymentInfo1).isNotEqualTo(paymentInfo2);
    }
}