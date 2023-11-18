package com.clothes.store.mapper;

import com.clothes.store.controller.request.SaleCardRequest;
import com.clothes.store.controller.request.SaleCashRequest;
import com.clothes.store.controller.response.SaleCardResponse;
import com.clothes.store.controller.response.SaleCashResponse;
import com.clothes.store.controller.web.request.SaleCardCreateRequest;
import com.clothes.store.controller.web.request.SaleCashCreateRequest;
import com.clothes.store.domain.SaleCash;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses=DateMapper.class)
public interface SaleMapper {
    SaleMapper instance = Mappers.getMapper(SaleMapper.class);
    // Sale Cash
    @Mapping(target = "client", source = "client")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "finalPrice", expression = "java(new java.math.BigDecimal(saleCash.finalPrice().doubleValue()))")
    SaleCashResponse mapToSaleCashResponse(SaleCash saleCash);

    @Mapping(target = "client.id", source = "clientId")
    SaleCash matToSaleCash(SaleCashRequest saleCashRequest);
    @Mapping(target = "client.id", source = "clientId")
    SaleCash matToSaleCash(SaleCashCreateRequest saleCashCreateRequest);

    // Sale Card
    @Mapping(target = "client", source = "client")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "finalPrice", expression = "java(new java.math.BigDecimal(saleCard.finalPrice().doubleValue()))")
    SaleCardResponse mapToSaleCardResponse(com.clothes.store.domain.SaleCard saleCard);

    @Mapping(target = "client.id", source = "clientId")
    com.clothes.store.domain.SaleCard matToSaleCard(SaleCardRequest SaleCardRequest);
    @Mapping(target = "client.id", source = "clientId")
    @Mapping(target = "date", source = "date")
    com.clothes.store.domain.SaleCard matToSaleCard(SaleCardCreateRequest saleCardCreateRequest);
}
