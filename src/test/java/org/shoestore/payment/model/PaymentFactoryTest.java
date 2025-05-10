package org.shoestore.payment.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.shoestore.payment.model.type.CardType;
import org.shoestore.payment.model.type.PaymentMethod;
import org.shoestore.payment.model.vo.PaymentInfo;
import org.shoestore.service.dto.PurchaseRequestDto;

class PaymentFactoryTest {

    private final PaymentInfo paymentInfo1 = new PaymentInfo(1L, 3.145, 230948L);

    @Test
    void CashPayment_create_테스트(){
        Payment payment = PaymentFactory.createPayment(PaymentMethod.CASH, paymentInfo1, null);
        assertThat(payment).isInstanceOf(CashPayment.class);
    }

    @Test
    void CreditCardPayment_create_테스트(){
        Payment payment = PaymentFactory.createPayment(PaymentMethod.CREDIT_CARD, paymentInfo1, CardType.HANA_CARD);
        assertThat(payment).isInstanceOf(CreditCardPayment.class);
    }
}