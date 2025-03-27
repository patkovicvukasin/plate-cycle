package com.platecycle.userservice.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Embeddable
public class UserRoleId implements Serializable {
    private static final long serialVersionUID = 533739441531982061L;
    @javax.validation.constraints.NotNull
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @javax.validation.constraints.NotNull
    @Column(name = "role_id", nullable = false)
    private UUID roleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserRoleId entity = (UserRoleId) o;
        return Objects.equals(this.roleId, entity.roleId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, userId);
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }
}