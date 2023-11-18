package com.clothes.store.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GarmentUpdate {
    private String description;

    private String type;

    private BigDecimal basePrice;
}
