package org.shoestore.domain.model.order;

import org.shoestore.domain.model.product.Product;

public class OrderLine {

    private final Product product; // 상품
    private boolean isCanceled; // 취소 여부

    // region constructor
    public OrderLine(Product product, boolean isCanceled) {
        this.product = product;
        this.isCanceled = isCanceled;
    }

    public OrderLine(Product product) {
        this.product = product;
        this.isCanceled = false;
    }
    // endregion

    // region getter logic
    public boolean isEqualProduct(Product product) {
        return this.product.equals(product);
    }

    public boolean isCanceled() {
        return this.isCanceled;
    }

    public boolean isPurchased(){
        return !this.isCanceled;
    }

    public double getSalesAmount(){
        return this.product.getSalesAmount();
    }
    // endregion

    // region getter logic
    /**
     * 주문 라인 취소 처리
     */
    public void cancel(){
        this.isCanceled = true;
    }
    // endregion
}
