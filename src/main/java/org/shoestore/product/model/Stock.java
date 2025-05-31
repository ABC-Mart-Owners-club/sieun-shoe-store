package org.shoestore.product.model;

import java.util.HashMap;
import org.shoestore.product.model.type.Supplier;

public class Stock {

    private final Long stockId;
    private final Supplier supplier; // 유통사 정보
    private final Long orderedAmount; // 주문한 재고
    private Long remainAmount; // 남은 재고
    private final Long storedDate; // 재고 들어온 날짜

    // region constructor
    public Stock(Long stockId, Supplier supplier, Long orderedAmount, Long remainAmount,
            Long storedDate) {
        this.stockId = stockId;
        this.supplier = supplier;
        this.orderedAmount = orderedAmount;
        this.remainAmount = remainAmount;
        this.storedDate = storedDate;
    }
    // endregion

    // region getter logic
    public Long getStockId(){
        return this.stockId;
    }

    public Long getRemainAmount(){
        return this.remainAmount;
    }

    public Long getStoredDate(){
        return this.storedDate;
    }

    /**
     * 재고 남아있는지 조회
     */
    public boolean isRemain(){
        return this.remainAmount > 0;
    }
    // endregion

    // region setter logic

    /**
     * 재고 감소
     */
    public void reduceStock(){
        this.remainAmount -= 1L;
    }
    // endregion
}
