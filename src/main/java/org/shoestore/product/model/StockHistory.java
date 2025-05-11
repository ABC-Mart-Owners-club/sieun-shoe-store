package org.shoestore.product.model;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class StockHistory {

    private final List<Stock> stocks;

    // region constructor
    public StockHistory(List<Stock> stocks) {
        // 재고 등록순으로 정렬
        stocks.sort((Comparator.comparingLong(Stock::getStoredDate)));
        this.stocks = stocks;
    }
    // endregion

    // region getter logic

    /**
     * 사용 가능한 재고번호 조회
     */
    public Long getUsableStockId() {
        return this.stocks.stream().filter(Stock::isRemain).findFirst().map(Stock::getStockId)
                .orElseThrow(()-> new RuntimeException("사용 가능한 재고 없음."));
    }
    // endregion

    // region setter logic
    /**
     * 재고 감소
     * <p>등록된 재고 순으로 감소</p>
     */
    public void reduceStock() {
        Stock remainedStock = stocks.stream().filter(stock -> stock.getRemainAmount() > 0).findFirst()
                .orElseThrow(() -> new RuntimeException("남은 재고 없음."));
        remainedStock.reduceStock();
    }
    // endregion
}
