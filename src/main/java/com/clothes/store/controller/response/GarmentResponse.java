package com.clothes.store.controller.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GarmentResponse {

    private Long id;

    private String description;

    private String type;

    private BigDecimal basePrice;

}
