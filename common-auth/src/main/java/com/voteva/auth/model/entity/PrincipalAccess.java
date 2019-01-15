package com.voteva.auth.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "access", schema = "auth")
public class PrincipalAccess {

    private int id;
    private int principalId;
    private String grant;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }

    public PrincipalAccess setId(int id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "principal_id", nullable = false)
    public int getPrincipalId() {
        return principalId;
    }

    public PrincipalAccess setPrincipalId(int principalId) {
        this.principalId = principalId;
        return this;
    }

    @Basic
    @Column(name = "grant", nullable = false)
    public String getGrant() {
        return grant;
    }

    public PrincipalAccess setGrant(String grant) {
        this.grant = grant;
        return this;
    }
}
