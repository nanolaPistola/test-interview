package com.multicert.test.service.dto;

import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.multicert.test.domain.Storage} entity.
 */
@EqualsAndHashCode
public class StorageDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String nif;

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

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StorageDTO)) {
            return false;
        }

        StorageDTO storageDTO = (StorageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, storageDTO.id);
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
            ", nif=" + getNif() +
            ", address='" + getAddress() + "'" +
            ", phone=" + getPhone() +
            "}";
    }
}
