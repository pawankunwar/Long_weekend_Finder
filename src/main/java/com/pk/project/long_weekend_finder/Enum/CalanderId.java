package com.pk.project.long_weekend_finder.Enum;

public enum CalanderId {
    IN("en.indian#holiday@group.v.calendar.google.com"),
    US("en.usa#holiday@group.v.calendar.google.com"),
    UK("en.uk#holiday@group.v.calendar.google.com");

    final String value;
    CalanderId(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}
