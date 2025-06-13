package org.shoestore.promotion.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.shoestore.promotion.model.Promotion;
import org.shoestore.promotion.model.type.PromotionType;
import org.shoestore.promotion.repository.PromotionReader;
import org.shoestore.promotion.repository.PromotionWriter;

class PromotionUseCaseTest {

    private final PromotionUseCase promotionUseCase = new PromotionUseCase(new PromotionWriter() {
        @Override
        public void savePromotionUsage(long orderId, long promotionId,
                double promotionDiscountAmount) {

        }
    }, new PromotionReader() {
        @Override
        public List<Promotion> getUserPromotionsByUserId(Long userId) {
            List<Promotion> promotions = new ArrayList<>();
            promotions.add(new Promotion(1L, 2L, PromotionType.FIXED_COUPON, 2000));
            promotions.add(new Promotion(1L, 2L, PromotionType.FIXED_COUPON, 4000));
            promotions.add(new Promotion(1L, 2L, PromotionType.RATE_COUPON, 11));
            return promotions;
        }

        @Override
        public double getPromotionSalesAmount(LocalDateTime startDateTime,
                LocalDateTime endDateTime) {

        }
    });

    @Test
    void 유저_최대할인_Promotion_조회_고정() {
        Promotion biggestPromotion = promotionUseCase.getBiggestPromotion(1L, 30000);
        assertThat(biggestPromotion.getPromotionType()).isEqualTo(PromotionType.FIXED_COUPON);
    }

    @Test
    void 유저_최대할인_Promotion_조회_비율() {
        Promotion biggestPromotion = promotionUseCase.getBiggestPromotion(1L, 40000);
        assertThat(biggestPromotion.getPromotionType()).isEqualTo(PromotionType.RATE_COUPON);
    }
}