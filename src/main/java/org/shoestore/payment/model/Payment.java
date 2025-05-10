package org.shoestore.payment.model;

import org.shoestore.payment.model.type.PaymentMethod;
import org.shoestore.payment.model.vo.PaymentInfo;

public abstract class Payment {

    protected final Long paymentId;
    protected final PaymentInfo paymentInfo;
    protected Double remainAmount;

    // region constructor
    public Payment(Long paymentId, PaymentInfo paymentInfo) {
        this.paymentId = paymentId;
        this.paymentInfo = paymentInfo;
        this.remainAmount = paymentInfo.amount();
    }

    public Payment(PaymentInfo paymentInfo) {
        this.paymentId = null;
        this.paymentInfo = paymentInfo;
        this.remainAmount = paymentInfo.amount();
    }

    // endregion

    // region getter logic

    /**
     * 결제 수단 조회
     */
    public abstract PaymentMethod getPaymentMethod();

    /**
     * 결제 금액 조회
     */
    public Double getPaymentAmount(){
        return this.paymentInfo.amount();
    }

    /**
     * 남은 결제 금액 조회
     */
    public Double getRemainAmount(){
        return this.remainAmount;
    }

    /**
     * 현금 구현체 여부 조회
     */
    public boolean isCashPayment(){
        return this.getPaymentMethod().isCash();
    }

    /**
     * 카드 구현체 여부 조회
     */
    public boolean isCreditCardPayment(){
        return this.getPaymentMethod().isCreditCard();
    }
    // endregion

    // region setter logic

    /**
     * 일부 금액 환불
     */
    public void refund(double refundAmount) {
        if (refundAmount > remainAmount) {
            throw new RuntimeException("환불금액이 잔여금액보다 큽니다");
        }
        this.remainAmount -= refundAmount;
    }

    /**
     * 전액 환부 처리
     */
    public void refundAll() {
        this.remainAmount = 0.0;
    }
    // endregion
}
