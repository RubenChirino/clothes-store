package com.clothes.store.domain;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Column;
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
@DiscriminatorValue("Card")
@Table(name="sale_card")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@SuperBuilder
public class SaleCard extends Sale implements Serializable {
	
    @Serial
    private static final long serialVersionUID = 7549753306871297143L;

    @Column(name = "sale_installment_quantity")
    private Integer installmentsQuantity;

    @Column(name = "sale_coefficient")
    private BigDecimal coefficientCard;

    public BigDecimal getPriceWithCharge() {
        return super.getBrutePrice()
                .add(super.getBrutePrice().multiply(new BigDecimal(0.01)))
                .add(coefficientCard
                        .multiply(new BigDecimal(installmentsQuantity)))
                .multiply(new BigDecimal(this.getItems().size()));
    }

    @Override
    public Double withCharge(Double basePrice) {
        return basePrice + (basePrice * 0.01 + coefficientCard.doubleValue() * installmentsQuantity.doubleValue());
    }

}
