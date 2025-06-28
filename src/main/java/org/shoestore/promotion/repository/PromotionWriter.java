package org.shoestore.promotion.repository;

public interface PromotionWriter {

    void savePromotionUsage(long orderId, long promotionId, double promotionDiscountAmount);
}
