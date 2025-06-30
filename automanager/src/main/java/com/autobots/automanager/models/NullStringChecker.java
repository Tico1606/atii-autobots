package com.autobots.automanager.models;

public class NullStringChecker {

    public boolean isNullOrEmpty(String value) {
        return value == null || value.isBlank();
    }
}
