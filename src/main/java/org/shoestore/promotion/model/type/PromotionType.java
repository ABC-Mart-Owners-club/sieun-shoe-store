package org.shoestore.promotion.model.type;

import java.util.function.BiFunction;
import org.shoestore.promotion.model.PromotionRule;
import org.shoestore.promotion.model.rule.FixedCouponRule;
import org.shoestore.promotion.model.rule.RateCouponRule;

public enum PromotionType {
    RATE_COUPON((discountValue, price) -> price * discountValue, new FixedCouponRule()),
    FIXED_COUPON((discountValue, price) -> discountValue, new RateCouponRule()),
    ;

    private final BiFunction<Double, Integer, Double> discountFunction;
    private final PromotionRule rule;

    PromotionType(BiFunction<Double, Integer, Double> discountFunction, PromotionRule rule) {
        this.discountFunction = discountFunction;
        this.rule = rule;
    }

    // 할인금액 조회
    public Double getDiscountAmount(int discountValue, double price) {
        // 할인 조건 만족여부 확인
        if (!this.rule.validateRule(price, discountValue)) {
            return 0.0;
        }
        return discountFunction.apply(price, discountValue);
    }
}
