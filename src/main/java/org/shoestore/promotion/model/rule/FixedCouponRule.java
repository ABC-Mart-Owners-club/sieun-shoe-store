package org.shoestore.promotion.model.rule;

import org.shoestore.promotion.model.PromotionRule;

public class FixedCouponRule implements PromotionRule {

    // 최소 주문 금액
    private static final Double MIN_PRICE_AMOUNT = 30000.0;

    @Override
    public boolean validateRule(double price, int discountValue) {
        return price >= MIN_PRICE_AMOUNT;
    }
}
