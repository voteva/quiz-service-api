package com.voteva.tests.security;

public enum Grants {

    TESTS_READ_GRANT("tests-read"),
    TESTS_WRITE_GRANT("tests-write");

    private String value;

    Grants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
