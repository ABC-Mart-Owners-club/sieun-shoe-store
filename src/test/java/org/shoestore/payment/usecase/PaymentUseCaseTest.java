package org.shoestore.payment.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.shoestore.order.model.Order;
import org.shoestore.payment.model.CashPayment;
import org.shoestore.payment.model.CreditCardPayment;
import org.shoestore.payment.model.Payment;
import org.shoestore.payment.model.type.CardType;
import org.shoestore.payment.model.vo.PaymentInfo;
import org.shoestore.payment.repository.PaymentReader;
import org.shoestore.payment.repository.PaymentWriter;
import org.shoestore.product.model.Product;
import org.shoestore.testprep.TestDomainModelPrep;

@ExtendWith(MockitoExtension.class)
class PaymentUseCaseTest {

    @Mock
    private PaymentWriter paymentWriter;
    @Mock
    private PaymentReader paymentReader;
    @InjectMocks
    private PaymentUseCase paymentUseCase;
    private TestDomainModelPrep prep;

    @BeforeEach
    void init(){
        this.prep = new TestDomainModelPrep();
    }

    @Test
    void pay_정상() {
        Order purchasedOrder =  prep.orderPurchased;
        List<Payment> payments = new ArrayList<>();
        payments.add(TestDomainModelPrep.createPaymentWithOrder(purchasedOrder.getOrderId(),
                500000.0, null, null));
        payments.add(TestDomainModelPrep.createPaymentWithOrder(purchasedOrder.getOrderId(),
                null, 50000.0, CardType.KB_CARD));

        paymentUseCase.pay(purchasedOrder, payments);

        verify(paymentWriter).savePayments(payments);
    }

    @Test
    void pay_금액_불일치_실패() {
        Order purchasedOrder =  prep.orderPurchased;
        List<Payment> payments = new ArrayList<>();
        payments.add(TestDomainModelPrep.createPaymentWithOrder(purchasedOrder.getOrderId(),
                500000.0, null, null));
        payments.add(TestDomainModelPrep.createPaymentWithOrder(purchasedOrder.getOrderId(),
                null, 50000.0, CardType.KB_CARD));

        assertThatThrownBy(() -> paymentUseCase.pay(purchasedOrder, payments))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("주문금액과 결제금액이 맞지 않음");
    }

    @Test
    void cancel() {
        Order canceledOrder = prep.orderCanceled;

        paymentUseCase.cancel(canceledOrder);

        verify(paymentWriter).updatePaymentCancelByOrderId(1L);
    }

    @Test
    void partialCancel_정상() {
        Order partialCanceledOrder = prep.orderPartialCanceled;
        Product partialCancelTargetProduct = prep.productNikeAirforce;
        List<Payment> payments = new ArrayList<>();
        payments.add(TestDomainModelPrep.createPaymentWithOrder(partialCanceledOrder.getOrderId(),
                500000.0, null, null));
        payments.add(TestDomainModelPrep.createPaymentWithOrder(partialCanceledOrder.getOrderId(),
                null, 50000.0, CardType.KB_CARD));
        when(paymentReader.getPaymentsByOrderId(partialCanceledOrder.getOrderId())).thenReturn(
                payments);

        paymentUseCase.partialCancel(partialCanceledOrder, partialCancelTargetProduct);

        Payment cardPayment = payments.stream().filter(Payment::isCreditCardPayment).findFirst().get();
        Payment cashPayment = payments.stream().filter(Payment::isCashPayment).findFirst().get();

        assertThat(cardPayment.getRemainAmount()).isEqualTo(0);
        assertThat(cashPayment.getRemainAmount()).isEqualTo(445000);
        verify(paymentWriter).updateAllPayment(payments);
    }

    @Test
    void partialCancel_정책_불일치_카드결제금액_더_큰_경우() {
        Order partialCanceledOrder = prep.orderPartialCanceled;
        Product partialCancelTargetProduct = prep.productNikeAirforce;
        List<Payment> payments = new ArrayList<>();
        payments.add(TestDomainModelPrep.createPaymentWithOrder(partialCanceledOrder.getOrderId(),
                50000.0, null, null));
        payments.add(TestDomainModelPrep.createPaymentWithOrder(partialCanceledOrder.getOrderId(),
                null, 500000.0, CardType.KB_CARD));
        when(paymentReader.getPaymentsByOrderId(partialCanceledOrder.getOrderId())).thenReturn(
                payments);

        assertThatThrownBy(() -> paymentUseCase.partialCancel(partialCanceledOrder,
                partialCancelTargetProduct))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("환불 정책으로 취소금액이 카드결제금액보다 클 때 환불 가능합니다.");
        verify(paymentWriter, never()).updateAllPayment(payments);
    }

    @Test
    void partialCancel_환불총액이_더_큰_경우() {
        Order partialCanceledOrder = prep.orderPartialCanceled;
        Product partialCancelTargetProduct = prep.productNikeAirforce;
        List<Payment> payments = new ArrayList<>();
        payments.add(TestDomainModelPrep.createPaymentWithOrder(partialCanceledOrder.getOrderId(),
                0.0, null, null));
        payments.add(TestDomainModelPrep.createPaymentWithOrder(partialCanceledOrder.getOrderId(),
                null, 50000.0, CardType.KB_CARD));
        when(paymentReader.getPaymentsByOrderId(partialCanceledOrder.getOrderId())).thenReturn(
                payments);

        assertThatThrownBy(() -> paymentUseCase.partialCancel(partialCanceledOrder,
                partialCancelTargetProduct))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("환불금액이 잔여금액보다 큽니다");
        verify(paymentWriter, never()).updateAllPayment(payments);
    }

    @Test
    void payFailure_주문정보_저장_전() {
        Order purchasedOrder = prep.orderPurchased;
        when(paymentReader.getPaymentsByOrderId(purchasedOrder.getOrderId())).thenReturn(null);

        paymentUseCase.payFailure(purchasedOrder);

        verify(paymentWriter, never()).deletePaymentsByOrderId(purchasedOrder.getOrderId());
    }

    @Test
    void payFailure_주문정보_저장_후() {
        Order purchasedOrder = prep.orderPurchased;
        List<Payment> payments = new ArrayList<>();
        payments.add(TestDomainModelPrep.createPaymentWithOrder(purchasedOrder.getOrderId(),
                0.0, null, null));
        payments.add(TestDomainModelPrep.createPaymentWithOrder(purchasedOrder.getOrderId(),
                null, 50000.0, CardType.KB_CARD));
        when(paymentReader.getPaymentsByOrderId(purchasedOrder.getOrderId())).thenReturn(
                payments);

        paymentUseCase.payFailure(purchasedOrder);

        verify(paymentWriter).deletePaymentsByOrderId(purchasedOrder.getOrderId());

    }

    @Test
    void cancelFailure() {
        Order canceldOrder = prep.orderCanceled;

        paymentUseCase.cancelFailure(canceldOrder);

        verify(paymentWriter).updatePaymentCancelFailure(canceldOrder.getOrderId());

    }

    @Test
    void partialCancelFailure() {
    }

    @Test
    void getCardSalesAmount() {
        paymentUseCase.getCardSalesAmount(CardType.KB_CARD);
        verify(paymentReader).getCardSalesAmount(CardType.KB_CARD);
    }
}