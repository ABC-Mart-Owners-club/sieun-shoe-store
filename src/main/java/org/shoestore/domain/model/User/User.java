package org.shoestore.domain.model.User;

public class User {

    private final String name; // 이름
    private final String phoneNumber; // 전화번호

    // region constructor
    public User(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    // endregion
}
