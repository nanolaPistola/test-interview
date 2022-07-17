package com.multicert.test.domain;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Storage.
 */
@Entity
@Table(name = "storage")
public class Storage implements Serializable {

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
    @Column(name = "nif", nullable = false)
    private String nif;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    @Min(value = 111111111)
    @Max(value = 999999999)
    private Integer phone;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Storage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Storage name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNif() {
        return this.nif;
    }

    public Storage nif(String nif) {
        this.setNif(nif);
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getAddress() {
        return this.address;
    }

    public Storage address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return this.phone;
    }

    public Storage phone(Integer phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Storage)) {
            return false;
        }
        return id != null && id.equals(((Storage) o).id);
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
            ", nif=" + getNif() +
            ", address='" + getAddress() + "'" +
            ", phone=" + getPhone() +
            "}";
    }
}
