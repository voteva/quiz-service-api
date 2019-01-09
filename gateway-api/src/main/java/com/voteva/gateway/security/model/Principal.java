package com.voteva.gateway.security.model;

import java.util.UUID;

public class Principal {

    private String subsystem;
    private UUID extId;

    public Principal(String subsystem, UUID extId) {
        this.subsystem = subsystem;
        this.extId = extId;
    }

    public String getSubsystem() {
        return subsystem;
    }

    public UUID getExtId() {
        return extId;
    }
}
