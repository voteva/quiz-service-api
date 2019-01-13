package com.voteva.auth.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "principal", schema = "auth")
public class PrincipalKey {

    private Integer id;
    private String extId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer getId() {
        return id;
    }

    public PrincipalKey setId(Integer id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "external_id", nullable = false)
    public String getExtId() {
        return extId;
    }

    public PrincipalKey setExtId(String extId) {
        this.extId = extId;
        return this;
    }
}
