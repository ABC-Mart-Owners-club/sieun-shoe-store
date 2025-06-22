package org.shoestore.promotion.model;

public interface PromotionRule {
    boolean validateRule(double price, double discountValue);
    double getDiscountValue(double price, double discountValue);
}
