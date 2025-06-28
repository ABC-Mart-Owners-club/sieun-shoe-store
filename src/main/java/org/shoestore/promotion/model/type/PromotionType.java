package org.shoestore.promotion.model.type;

import org.shoestore.promotion.model.PromotionRule;
import org.shoestore.promotion.model.rule.FixedCouponRule;
import org.shoestore.promotion.model.rule.RateCouponRule;

public enum PromotionType {
    RATE_COUPON(new RateCouponRule()),
    FIXED_COUPON(new FixedCouponRule()),
    ;

    private final PromotionRule rule;

    PromotionType(PromotionRule rule) {
        this.rule = rule;
    }

    // 할인금액 조회
    public Double getDiscountAmount(double discountValue, double price) {
        // 할인 조건 만족여부 확인
        if (!this.rule.validateRule(price, discountValue)) {
            return 0.0;
        }
        return this.rule.getDiscountValue(price, discountValue);
    }
}
