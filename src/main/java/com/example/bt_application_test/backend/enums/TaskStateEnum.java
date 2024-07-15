package com.example.bt_application_test.backend.enums;

public enum TaskStateEnum {
    NEW("New"),
    IN_PROGRESS("In Progress"),
    CLOSED("Closed"),
    CANCELED("Canceled");

    private String displayName;

    TaskStateEnum() {
        this.displayName = name();
    }

    TaskStateEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}