package com.voteva.auth.model.entity;

public class PrincipalKey {

    private String subsystem;
    private String extId;

    public PrincipalKey(String subsystem, String extId) {
        this.subsystem = subsystem;
        this.extId = extId;
    }

    public String getSubsystem() {
        return subsystem;
    }

    public PrincipalKey setSubsystem(String subsystem) {
        this.subsystem = subsystem;
        return this;
    }

    public String getExtId() {
        return extId;
    }

    public PrincipalKey setExtId(String extId) {
        this.extId = extId;
        return this;
    }
}
