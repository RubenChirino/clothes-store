package com.clothes.store.controller.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {
    private Long id;

    private ClientResponse client;

    private String date;

    private List<ItemResponse> items;

    private BigDecimal finalPrice;

}
