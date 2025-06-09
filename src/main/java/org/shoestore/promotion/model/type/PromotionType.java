package org.shoestore.promotion.model.type;

import java.util.function.BiFunction;
import org.shoestore.promotion.model.PromotionRule;
import org.shoestore.promotion.model.rule.FixedCouponRule;
import org.shoestore.promotion.model.rule.RateCouponRule;

public enum PromotionType {
    RATE_COUPON((discountValue, price) -> Math.min(50000,(price * discountValue)/100), new RateCouponRule()),
    FIXED_COUPON((discountValue, price) -> discountValue, new FixedCouponRule()),
    ;

    private final BiFunction<Double, Double, Double> discountFunction;
    private final PromotionRule rule;

    PromotionType(BiFunction<Double, Double, Double> discountFunction, PromotionRule rule) {
        this.discountFunction = discountFunction;
        this.rule = rule;
    }

    // 할인금액 조회
    public Double getDiscountAmount(double discountValue, double price) {
        // 할인 조건 만족여부 확인
        if (!this.rule.validateRule(price, discountValue)) {
            return 0.0;
        }
        return discountFunction.apply(discountValue, price);
    }
}
