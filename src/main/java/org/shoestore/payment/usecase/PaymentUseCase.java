package org.shoestore.payment.usecase;

import java.util.List;
import org.shoestore.order.model.Order;
import org.shoestore.payment.model.Payment;
import org.shoestore.payment.model.type.CardType;
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
     * 결제 취소
     */
    public void cancel(Order order) {
        paymentWriter.updatePaymentCancel(order.getOrderId());
    }

    /**
     * 부분 결제 취소
     */
    public void partialCancel(Order order) {
        if (!order.isCanceled()) {
            return;
        }
        paymentWriter.updatePaymentCancel(order.getOrderId());
    }

    /**
     * 결제 실패 보상로직
     */
    public void payFailure(Order order) {
        Long orderId = order.getOrderId();
        List<Payment> payments =  paymentReader.getPaymentsByOrderId(orderId);
        if (payments.isEmpty()) {
            return;
        }
        paymentWriter.deletePaymentsByOrderId(orderId);
    }

    /**
     * 결제 취소 실패 보상로직
     */
    public void cancelFailure(Order order) {
        paymentWriter.updatePaymentCancelFailure(order.getOrderId());
    }

    /**
     * 결제 부분 취소 실패 보상로직
     */
    public void partialCancelFailure(Order order, boolean isCanceledOrder) {
        if (isCanceledOrder) {
            paymentWriter.updatePaymentCancelFailure(order.getOrderId());
        }
    }

    /**
     * 카드사의 총 판매금액 조회
     */
    public double getCardSalesAmount(CardType cardType) {
        return paymentReader.getCardSalesAmount(cardType);
    }
}
