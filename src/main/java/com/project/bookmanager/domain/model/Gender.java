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

    @JsonCreator
    public static Gender fromValue(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.value.equalsIgnoreCase(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Gênero inválido: " + value);
    }
}
