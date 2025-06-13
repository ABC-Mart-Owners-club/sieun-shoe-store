package org.shoestore.promotion.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.shoestore.promotion.model.Promotion;


/// 할인 테이블 구성
/// Promotion (promotionId, userId, promotionType, promotionValue)
/// PromotionUsage (promotionUsageId, promotionId, orderId, promotionDiscountAmount)
public interface PromotionReader {

    List<Promotion> getUserPromotionsByUserId(Long userId);

    ///StockDtl 기준, 등록일
    double getPromotionSalesAmount(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
