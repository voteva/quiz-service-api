package com.voteva.gateway.security.model;

public class AuthenticationData {

    private final Authentication authentication;
    private final Session session;
    private final String stringRepresentation;

    public AuthenticationData(Authentication authentication, Session session) {
        this.authentication = authentication;
        this.session = session;
        this.stringRepresentation = String.format("AuthenticationData (user: %s, session: %s)",
                session.getUser().getUuid(), session.getSessionUid());
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}
