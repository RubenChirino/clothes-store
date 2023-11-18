package com.clothes.store.domain;

import java.io.Serial;
import java.io.Serializable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@PrimaryKeyJoinColumn(name = "sale_id")
@DiscriminatorValue("Cash")
@Table(name="sale_cash")

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(force = true)
@SuperBuilder
public class SaleCash extends Sale implements Serializable {
    @Serial
    private static final long serialVersionUID = -8393218825317899807L;

    @Override
    public Double withCharge(Double basePrice) {
        return basePrice;
    }
}
