package com.voteva.gateway.redis.model;

import java.util.UUID;

public class Task {

    private UUID id = UUID.randomUUID();
    private String content;

    public UUID getId() {
        return id;
    }

    public Task setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Task setContent(String content) {
        this.content = content;
        return this;
    }
}
