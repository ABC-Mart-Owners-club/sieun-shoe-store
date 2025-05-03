package org.shoestore.payment.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.shoestore.payment.model.type.CardType;
import org.shoestore.payment.model.vo.PaymentInfo;

class CashPaymentTest {
    private final PaymentInfo paymentInfo1 = new PaymentInfo(1L, 3.145, 230948L);

    @Test
    void 생성자_테스트(){
        CashPayment creditCardPayment = new CashPayment(paymentInfo1);
        assertThat(creditCardPayment.paymentId).isNull();
        assertThat(creditCardPayment.paymentInfo).isEqualTo(paymentInfo1);
    }

    @Test
    void paymentId_생성자_테스트(){
        CashPayment creditCardPayment = new CashPayment(124L, paymentInfo1);
        assertThat(creditCardPayment.paymentId).isEqualTo(124L);
        assertThat(creditCardPayment.paymentInfo).isEqualTo(paymentInfo1);
    }
}