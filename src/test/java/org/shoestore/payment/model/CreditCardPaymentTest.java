package org.shoestore.payment.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.shoestore.payment.model.type.CardType;
import org.shoestore.payment.model.vo.PaymentInfo;

class CreditCardPaymentTest {

    private final PaymentInfo paymentInfo1 = new PaymentInfo(1L, 3.145, 230948L);

    @Test
    void 생성자_테스트(){
        CreditCardPayment creditCardPayment = new CreditCardPayment(paymentInfo1, CardType.KB_CARD);
        assertThat(creditCardPayment.paymentId).isNull();
        assertThat(creditCardPayment.paymentInfo).isEqualTo(paymentInfo1);
        assertThat(creditCardPayment.getCardType()).isEqualTo(CardType.KB_CARD);
    }

    @Test
    void paymentId_생성자_테스트(){
        CreditCardPayment creditCardPayment = new CreditCardPayment(124L, paymentInfo1, CardType.KB_CARD);
        assertThat(creditCardPayment.paymentId).isEqualTo(124L);
        assertThat(creditCardPayment.paymentInfo).isEqualTo(paymentInfo1);
        assertThat(creditCardPayment.getCardType()).isEqualTo(CardType.KB_CARD);
    }
}