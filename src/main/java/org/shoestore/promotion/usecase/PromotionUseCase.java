package org.shoestore.promotion.usecase;

import java.util.List;
import java.util.Optional;
import org.shoestore.promotion.model.Promotion;
import org.shoestore.promotion.repository.PromotionReader;
import org.shoestore.promotion.repository.PromotionWriter;

public class PromotionUseCase {
    private final PromotionWriter promotionWriter;
    private final PromotionReader promotionReader;

    public PromotionUseCase(PromotionWriter promotionWriter, PromotionReader promotionReader) {
        this.promotionWriter = promotionWriter;
        this.promotionReader = promotionReader;
    }

    public Promotion getBiggestPromotion(Long userId, double price) {
        List<Promotion> userDiscountsByUserId = promotionReader.getUserPromotionsByUserId(userId);
        Optional<Promotion> maxDiscount = userDiscountsByUserId.stream()
                .max(((o1, o2) -> (int) (o1.getDiscountAmount(price) - o2.getDiscountAmount(
                        price))));
        return maxDiscount.orElse(null);
    }
}
