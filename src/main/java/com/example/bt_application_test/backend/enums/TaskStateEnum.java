package com.example.bt_application_test.backend.enums;

public enum TaskStateEnum {
    New,
    InProgress("In Progress"),
    Closed,
    Canceled;

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