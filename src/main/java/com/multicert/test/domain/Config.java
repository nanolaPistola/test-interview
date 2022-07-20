package com.multicert.test.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Storage.
 */
@Entity
@Table(name = "config", uniqueConstraints = {@UniqueConstraint(columnNames = {"country"})})
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "actions", nullable = false)
    private String actions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

}
