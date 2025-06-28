package org.shoestore.promotion.model.rule;

import org.shoestore.promotion.model.PromotionRule;

public class RateCouponRule implements PromotionRule {

    // 최대 할인 금액
    private static final Double MAX_PRICE_AMOUNT = 500000.0;
    public static final Double MAX_DISCOUNT_AMOUNT = 50000.0;

    @Override
    public boolean validateRule(double price, double discountValue) {

        return price <= MAX_PRICE_AMOUNT;
    }

    @Override
    public double getDiscountValue(double price, double discountValue) {
        return Math.min(MAX_DISCOUNT_AMOUNT,(price * discountValue)/100);
    }
}
