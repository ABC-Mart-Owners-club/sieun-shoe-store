package org.shoestore.promotion.model.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.shoestore.promotion.model.Promotion;

class PromotionTypeTest {

    @Test
    void 할인_테스트_고정할인_최소주문금액미충족(){
        Promotion promotion = new Promotion(1L, 2L, PromotionType.FIXED_COUPON, 5000);
        double discountAmount = promotion.getDiscountAmount(5000);
        assertThat(discountAmount).isEqualTo(0);
    }

    @Test
    void 할인_테스트_고정할인_최소주문금액충족(){
        Promotion promotion = new Promotion(1L, 2L, PromotionType.FIXED_COUPON, 5000);
        double discountAmount = promotion.getDiscountAmount(50000);
        assertThat(discountAmount).isEqualTo(5000);
    }

    @Test
    void 할인_테스트_퍼센트할인_최대할인금액미만(){
        Promotion promotion = new Promotion(1L, 2L, PromotionType.RATE_COUPON, 50);
        double discountAmount = promotion.getDiscountAmount(50000);
        assertThat(discountAmount).isEqualTo(25000);
    }

    @Test
    void 할인_테스트_퍼센트할인_최대할인금액제한(){
        Promotion promotion = new Promotion(1L, 2L, PromotionType.RATE_COUPON, 50);
        double discountAmount = promotion.getDiscountAmount(150000);
        assertThat(discountAmount).isEqualTo(50000);
    }

    @Test
    void 할인_테스트_퍼센트할인_적용불가금액(){
        Promotion promotion = new Promotion(1L, 2L, PromotionType.RATE_COUPON, 50);
        double discountAmount = promotion.getDiscountAmount(5550000);
        assertThat(discountAmount).isEqualTo(0);
    }
}