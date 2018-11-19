package com.voteva.gateway.security.model;

import java.util.UUID;

public class User {

    private UUID uuid = UUID.fromString("21793aac-0171-42c1-9c66-7284ec24a330");
    private String login;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
