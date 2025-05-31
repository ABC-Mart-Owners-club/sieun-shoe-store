package org.shoestore.payment.usecase;

import java.util.List;
import java.util.Optional;
import org.shoestore.order.model.Order;
import org.shoestore.payment.model.Payment;
import org.shoestore.payment.model.type.CardType;
import org.shoestore.payment.model.type.PaymentMethod;
import org.shoestore.payment.repository.PaymentReader;
import org.shoestore.payment.repository.PaymentWriter;
import org.shoestore.product.model.Product;

public class PaymentUseCase {

    private final PaymentWriter paymentWriter;
    private final PaymentReader paymentReader;

    public PaymentUseCase(PaymentWriter paymentWriter, PaymentReader paymentReader) {
        this.paymentWriter = paymentWriter;
        this.paymentReader = paymentReader;
    }

    /**
     * 결제
     * <p>1. 주문 금액과 결제 금액 일치여부 확인</p>
     * <p>2. 주문정보 저장</p>
     */
    public void pay(Order order, List<Payment> payments) {
        double totalPaymentAmount = payments.stream().mapToDouble(Payment::getPaymentAmount).sum();
        // 1.주문 금액과 결제 금액 일치여부 확인
        if (order.getTotalPrice() != totalPaymentAmount) {
            throw new RuntimeException("주문금액과 결제금액이 맞지 않음");
        }
        paymentWriter.savePayments(payments);
    }

    /**
     * 결제 취소 및 환불 처리
     * <p>1. 결제수단 별 남은 금액 0원 업데이트 처리</p>
     */
    public void cancel(Order order) {
        // 1. 결제수단 별 남은 금액 0원 업데이트 처리(DB 업데이트)
        paymentWriter.updatePaymentCancelByOrderId(order.getOrderId());
    }

    /**
     * 부분 결제 취소 및 부분 환불 처리
     * <p>Rule 1 : 부분취소 시 카드결제 내역을 먼저 취소 한다. (카드사간 순서는 없음)</p>
     * <p>Rule 2 : 카드결제내역 취소 시, 취소금액이 결제금액보다 큰 경우에만 진행한다.</p>
     * <p>1. 취소된 결제면 취소 업데이트</p>
     * <p>2. 부분취소 시 금액비교 및 취소 내용 업데이트</p>
     */
    public void partialCancel(Order order, Product product) {
        // 1. 취소된 결제면 취소 업데이트
        if (order.isCanceled()) {
            paymentWriter.updatePaymentCancelByOrderId(order.getOrderId());
            return;
        }

        List<Payment> payments = paymentReader.getPaymentsByOrderId(order.getOrderId());
        // 2. 부분취소 금액비교
        double productRefundAmount = order.getProductPurchasePrice(product);
        double creditCardRemainAmount = payments.stream().filter(Payment::isCreditCardPayment)
                .mapToDouble(Payment::getRemainAmount).sum();
        if (creditCardRemainAmount > productRefundAmount) {
            throw new RuntimeException("환불 정책으로 취소금액이 카드결제금액보다 클 때 환불 가능합니다.");
        }
        double cashRefundAmount = productRefundAmount - creditCardRemainAmount;

        payments.stream().filter(Payment::isCreditCardPayment).forEach(Payment::refundAll);
        payments.stream().filter(Payment::isCashPayment).findFirst().ifPresentOrElse(
                payment -> payment.refund(cashRefundAmount), // if present
                () -> { // else
                    if (cashRefundAmount > 0) {
                        throw new RuntimeException("결제 정보가 일치하지 않습니다.");
                    }
                }
        );
        paymentWriter.updateAllPayment(payments);
    }

    /**
     * 결제 실패 보상로직
     * <p>orderId 기준 저장된 결제내역 존재여부 확인</p>
     */
    public void payFailure(Order order) {
        Long orderId = order.getOrderId();
        List<Payment> payments = paymentReader.getPaymentsByOrderId(orderId);
        if (payments == null || payments.isEmpty()) {
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
    public void partialCancelFailure(Order order, Product product) {
        // 진짜 비상인게, 해당 금액만큼 취소된 상태인지를 알 수 있는 방법이 없다는거임.
    }

    /**
     * 카드사의 총 판매금액 조회
     */
    public double getCardSalesAmount(CardType cardType) {
        return paymentReader.getCardSalesAmount(cardType);
    }
}
