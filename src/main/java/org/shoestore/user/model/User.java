package org.shoestore.user.model;

public class User {

    private final Long userId;
    private final String name; // 이름
    private final String phoneNumber; // 전화번호

    // region constructor
    public User(String name, String phoneNumber) {
        this.userId = null;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public User(Long userId, String name, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
// endregion

    //region getter logic
    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    // endregion
}
