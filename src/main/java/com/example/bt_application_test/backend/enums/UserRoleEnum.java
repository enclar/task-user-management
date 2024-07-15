package com.example.bt_application_test.backend.enums;

public enum UserRoleEnum {
    USER("User"),
    ADMIN("Admin");

    private String displayName;

    UserRoleEnum() {
        this.displayName = name();
    }

    UserRoleEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
