package com.multicert.test.domain;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Storage.
 */
@Entity
@Table(name = "client", uniqueConstraints = {@UniqueConstraint(columnNames = {"fiscal_number", "country"})})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Length(min = 9, max = 9)
    @Column(name = "fiscal_number", nullable = false)
    private String fiscalNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    @Min(value = 111111111)
    @Max(value = 999999999)
    private Integer phone;

    @Column(name = "country")
    private String country;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Client name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFiscalNumber() {
        return this.fiscalNumber;
    }

    public Client nif(String nif) {
        this.setFiscalNumber(nif);
        return this;
    }

    public void setFiscalNumber(String nif) {
        this.fiscalNumber = nif;
    }

    public String getAddress() {
        return this.address;
    }

    public Client address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return this.phone;
    }

    public Client phone(Integer phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Storage{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fiscalNumber=" + getFiscalNumber() +
            ", address='" + getAddress() + "'" +
            ", phone=" + getPhone() +
            "}";
    }
}
