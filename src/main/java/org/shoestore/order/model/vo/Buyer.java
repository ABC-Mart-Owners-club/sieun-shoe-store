package org.shoestore.order.model.vo;

import org.shoestore.user.model.User;

public record Buyer(
        String name,
        String phone
) {

    public Buyer(User user) {
        this(user.getName(), user.getPhoneNumber());
    }
}
