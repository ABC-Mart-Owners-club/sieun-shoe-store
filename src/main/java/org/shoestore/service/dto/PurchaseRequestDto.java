package org.shoestore.service.dto;

import java.util.List;
import org.shoestore.payment.model.type.CardType;

public class PurchaseRequestDto {

    private List<Long> productIds;
    private Long userId;
    private String name; // 이름
    private String phoneNumber; // 전화번호

    private Double cashAmount;
    private Double cardAmount;
    private CardType cardType;
    private Long promotionId;

    public List<Long> getProductIds() {
        return productIds;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Double getCashAmount() {
        return cashAmount;
    }

    public Double getCardAmount() {
        return cardAmount;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Long getPromotionId() {
        return promotionId;
    }
}
