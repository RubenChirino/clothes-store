package com.clothes.store.controller.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleCardCreateRequest {
    private Long clientId;

    private String date;

    private Integer installmentsQuantity;

}
