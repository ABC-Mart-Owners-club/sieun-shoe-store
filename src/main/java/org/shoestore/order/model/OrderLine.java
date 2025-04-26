package org.shoestore.order.model;

import org.shoestore.order.model.vo.OrderItem;
import org.shoestore.product.model.Product;

public class OrderLine {

    private final Long orderLineId;
    private final OrderItem orderItem; // 상품
    private boolean isCanceled; // 취소 여부

    // region constructor

    public OrderLine(Long orderLineId, OrderItem orderItem, boolean isCanceled) {
        this.orderLineId = orderLineId;
        this.orderItem = orderItem;
        this.isCanceled = isCanceled;
    }

    public OrderLine(Product product, boolean isCanceled) {
        this.orderLineId = null;
        this.orderItem = new OrderItem(product);
        this.isCanceled = isCanceled;
    }

    public OrderLine(Product product) {
        this.orderLineId = null;
        this.orderItem = new OrderItem(product);
        this.isCanceled = false;
    }
    // endregion

    // region getter logic
    public boolean isEqualProduct(Long productId) {
        return this.orderItem.productId().equals(productId);
    }

    public boolean isCanceled() {
        return this.isCanceled;
    }

    public boolean isPurchased(){
        return !this.isCanceled;
    }

    public double getSalesAmount(){
        return this.orderItem.purchasedPrice();
    }
    // endregion

    // region getter logic
    /**
     * 주문 라인 취소 처리
     */
    public void cancel(){
        this.isCanceled = true;
    }

    /**
     * 주문 라인 취소 처리 실패 보상 로직
     */
    public void undoCancel() {
        this.isCanceled = false;
    }
    // endregion
}
