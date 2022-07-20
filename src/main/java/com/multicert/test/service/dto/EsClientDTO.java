package com.multicert.test.service.dto;

import com.multicert.test.domain.Client;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link Client} entity.
 */
public class EsClientDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String dni;

    private String address;

    private Integer phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StorageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dni=" + getDni() +
            ", address='" + getAddress() + "'" +
            ", phone=" + getPhone() +
            "}";
    }
}
