package org.shoestore.promotion.repository;

import java.util.List;
import org.shoestore.promotion.model.Promotion;

public interface PromotionReader {

    List<Promotion> getUserPromotionsByUserId(Long userId);
}
