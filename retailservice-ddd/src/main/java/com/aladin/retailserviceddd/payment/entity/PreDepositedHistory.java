package com.aladin.retailserviceddd.payment.entity;

import com.aladin.retailserviceddd.common.intity.AbstractAuditingEntity;
import reactor.util.annotation.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "retail_pre_deposited_history")
public class PreDepositedHistory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NonNull
    private Long preDepositedId;

    @NonNull
    private Long amount;

    private String description;

    private String type;

    public Long getId() {
        return id;
    }

    @NonNull
    public Long getPreDepositedId() {
        return preDepositedId;
    }

    public void setPreDepositedId(@NonNull Long preDepositedId) {
        this.preDepositedId = preDepositedId;
    }

    @NonNull
    public Long getAmount() {
        return amount;
    }

    public void setAmount(@NonNull Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PreDepositedHistory{" +
                "id=" + id +
                ", preDepositedId=" + preDepositedId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
