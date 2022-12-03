package com.aladin.retailserviceddd.payment.entity;

import com.aladin.retailserviceddd.common.intity.AbstractAuditingEntity;
import reactor.util.annotation.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "retail_pre_deposited")
public class PreDeposited extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NonNull
    private Long surplus;

    @NonNull
    private String username;

    @NonNull
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSurplus() {
        return surplus;
    }

    public void setSurplus(Long surplus) {
        this.surplus = surplus;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void insertMoney(Long amount) {
        this.surplus += amount;
    }

    public void depositMoney(Long amount) {
        this.surplus -= amount;
    }

    public Boolean enoughMoney(Long money) {
        return this.surplus.compareTo(money) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreDeposited that = (PreDeposited) o;
        return Objects.equals(id, that.id) && surplus.equals(that.surplus) && username.equals(that.username) && type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surplus, username, type);
    }

    @Override
    public String toString() {
        return "PreDeposited{" +
                "id=" + id +
                ", surplus=" + surplus +
                ", username='" + username + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
