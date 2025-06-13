package org.shoestore.promotion.model;

public interface PromotionRule {
    boolean validateRule(double price, double discountValue);
}
