package org.shoestore.domain.model.order.vo;

import org.shoestore.domain.model.User.User;

public record Buyer(
        String name,
        String phone
) {

    public Buyer(User user) {
        this(user.getName(), user.getPhoneNumber());
    }
}
