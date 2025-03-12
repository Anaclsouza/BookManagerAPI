package com.project.bookmanager.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    ROMANCE("romance"),
    TERROR("terror"),
    SUSPENSE("suspense"),
    FICCAO("ficcao"),
    FANTASIA("fantasia"),
    AUTOAJUDA("autoajuda");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
    public static Gender toEnum(String statusString) {
        for (Gender gender : Gender.values()) {
            if (gender.getValue().equalsIgnoreCase(statusString)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + statusString);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
