package org.shoestore.service.dto;

import java.util.List;
import org.shoestore.payment.model.type.CardType;

public class PurchaseRequestDto {

    public List<Long> productIds;
    public Long userId;
    public String name; // 이름
    public String phoneNumber; // 전화번호

    public Long cashAmount;
    public Long cardAmount;
    public CardType cardType;

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

    public Long getCashAmount() {
        return cashAmount;
    }

    public Long getCardAmount() {
        return cardAmount;
    }

    public CardType getCardType() {
        return cardType;
    }
}
