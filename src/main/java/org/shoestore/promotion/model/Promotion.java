package org.shoestore.promotion.model;

import org.shoestore.promotion.model.type.PromotionType;

public class Promotion {
    private final Long promotionId;
    private final Long userId;
    private final PromotionType promotionType;
    private final Integer discountValue;

    public Promotion(Long id, Long userId, PromotionType promotionType, Integer discountValue) {
        this.promotionId = id;
        this.userId = userId;
        this.promotionType = promotionType;
        this.discountValue = discountValue;
    }

    public long getPromotionId() {
        return promotionId;
    }

    public double getDiscountAmount(double price) {
        return promotionType.getDiscountAmount(this.discountValue, price);
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }
}
