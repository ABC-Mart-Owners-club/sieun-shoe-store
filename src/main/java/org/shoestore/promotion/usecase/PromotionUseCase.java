package org.shoestore.promotion.usecase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.shoestore.Util;
import org.shoestore.order.model.Order;
import org.shoestore.promotion.model.Promotion;
import org.shoestore.promotion.repository.PromotionReader;
import org.shoestore.promotion.repository.PromotionWriter;
import org.shoestore.service.dto.PromotionRequestDto;

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

    public double getPromotionSalesAmount(PromotionRequestDto req) {
        LocalDateTime startDateTime = Util.milliToLocalDateTime(req.startTimestamp);
        LocalDateTime endDateTime = Util.milliToLocalDateTime(req.endTimestamp);
        return promotionReader.getPromotionSalesAmount(startDateTime, endDateTime);
    }

    /**
     * 프로모션 사용 처리
     */
    public void use(Order order, Promotion promotion) {
        double promotionDiscountAmount = promotion.getDiscountAmount(order.getTotalPrice());
        promotionWriter.savePromotionUsage(order.getOrderId(), promotion.getPromotionId(), promotionDiscountAmount);
    }
}
