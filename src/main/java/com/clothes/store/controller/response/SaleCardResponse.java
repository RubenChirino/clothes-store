package com.clothes.store.controller.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class SaleCardResponse extends SaleResponse {
    private Integer installmentsQuantity;

    private BigDecimal coefficientCard;

}
