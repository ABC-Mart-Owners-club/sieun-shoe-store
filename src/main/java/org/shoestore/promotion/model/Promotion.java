package org.shoestore.promotion.model;

import org.shoestore.promotion.model.type.PromotionType;

public class Promotion {
    private final Long promotionId;
    private final PromotionType promotionType;
    private final Integer discountValue;

    public Promotion(Long id, PromotionType promotionType, Integer discountValue) {
        this.promotionId = id;
        this.promotionType = promotionType;
        this.discountValue = discountValue;
    }

    public double getDiscountAmount(double price) {
        return promotionType.getDiscountAmount(this.discountValue, price);
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }
}
