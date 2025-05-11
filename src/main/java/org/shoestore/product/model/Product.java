package org.shoestore.product.model;

import java.util.ArrayList;
import java.util.Objects;

public class Product {

    private final Long productId; // 상품 ID
    private final String modelName; // 모델 명
    private final String brand; // 브랜드
    private final double price; // 가격
    private final StockHistory stockHistory; // 재고 내역

    // region constructor
    public Product(Long productId, String modelName, String brand, double price, StockHistory stockHistory) {
        this.productId = productId;
        this.modelName = modelName;
        this.brand = brand;
        this.price = price;
        this.stockHistory = stockHistory;
    }

    public Product(String modelName, String brand, double price) {
        this.productId = null;
        this.modelName = modelName;
        this.brand = brand;
        this.price = price;
        this.stockHistory = new StockHistory(new ArrayList<>());
    }
    // endregion

    // region getter logic
    public Long getProductId() {
        return this.productId;
    }

    public String getModelName() {
        return this.modelName;
    }

    public String getBrand() {
        return this.brand;
    }

    public double getSalesAmount(){
        return this.price;
    }

    /**
     * 사용 가능한 재고번호 조회
     */
    public Long getUsableStockId() {
        return this.stockHistory.getUsableStockId();
    }

    public StockHistory getStockHistory(){
        return this.stockHistory;
    }
    // endregion

    // region setter logic

    /**
     * 판매
     */
    public void sale() {
        this.stockHistory.reduceStock();
    }
    // endregion

    // region Equals Override
    @Override
    public int hashCode() {
        return Objects.hash(this.productId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Product product)) {
            return false;
        }
        return Objects.equals(productId, product.productId);
    }
    // endregion
}
