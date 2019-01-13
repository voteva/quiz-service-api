package com.voteva.auth.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "credentials", schema = "auth")
public class Credentials {

    private int id;
    private int principalId;
    private String login;
    private String secret;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }

    public Credentials setId(int id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "principal_id", nullable = false)
    public int getPrincipalId() {
        return principalId;
    }

    public Credentials setPrincipalId(int principalId) {
        this.principalId = principalId;
        return this;
    }

    @Basic
    @Column(name = "login", nullable = false)
    public String getLogin() {
        return login;
    }

    public Credentials setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    @Basic
    @Column(name = "secret", nullable = false)
    public Credentials setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", principalId=" + principalId +
                ", login=" + login +
                ", secret=" + "[SECURED]" +
                '}';
    }
}
