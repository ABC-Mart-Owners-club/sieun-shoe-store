package org.shoestore.promotion.model;

public interface PromotionRule {
    boolean validateRule(double price, int discountValue);
}
