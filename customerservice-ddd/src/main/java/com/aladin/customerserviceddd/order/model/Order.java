package com.aladin.customerserviceddd.order.model;

import com.aladin.customerserviceddd.common.intity.AbstractAuditingEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "retail_order")
public class Order extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String username;

    private Long totalMoney;

    private Long preDepositedId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Long getPreDepositedId() {
        return preDepositedId;
    }

    public void setPreDepositedId(Long preDepositedId) {
        this.preDepositedId = preDepositedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(username, order.username) && Objects.equals(totalMoney, order.totalMoney) && Objects.equals(preDepositedId, order.preDepositedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, totalMoney, preDepositedId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", totalMoney=" + totalMoney +
                ", preDepositedId=" + preDepositedId +
                '}';
    }
}

