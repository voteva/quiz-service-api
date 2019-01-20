package com.voteva.gateway.redis.model;

public enum Topic {

    TESTS_DELETE("queue#testDelete"),
    TESTS_DELETE_30("queue#testDelete30"),
    TESTS_DELETE_60("queue#testDelete60");

    private String name;

    Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
