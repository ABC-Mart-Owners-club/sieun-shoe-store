package org.shoestore.domain.model.product;

import java.util.Objects;

public class Product {

    private final String modelName; // 모델 명
    private final String brand; // 브랜드
    private final double price; // 가격

    // region constructor
    public Product(String modelName, String brand, double price) {
        this.modelName = modelName;
        this.brand = brand;
        this.price = price;
    }
    // endregion

    // region getter logic
    public double getSalesAmount(){
        return this.price;
    }
    // endregion

    // region Equals Override
    @Override
    public int hashCode() {
        return Objects.hash(this.modelName, this.brand);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Product product)) {
            return false;
        }
        return Objects.equals(modelName, product.modelName) &&
                Objects.equals(brand, product.brand);
    }
    // endregion
}
