package org.shoestore.promotion.model.rule;

import org.shoestore.promotion.model.PromotionRule;

public class RateCouponRule implements PromotionRule {

    // 최대 할인 금액
    private static final Double MAX_DISCOUNT_AMOUNT = 50000.0;

    @Override
    public boolean validateRule(double price, int discountValue) {

        return price * discountValue <= MAX_DISCOUNT_AMOUNT;
    }
}
