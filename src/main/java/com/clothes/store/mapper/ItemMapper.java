package com.clothes.store.mapper;

import com.clothes.store.controller.request.ItemInsert;
import com.clothes.store.controller.request.ItemUpdate;
import com.clothes.store.controller.response.ItemResponse;
import com.clothes.store.controller.web.request.SaleItemCreateRequest;
import com.clothes.store.domain.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ItemMapper {
    ItemMapper instance = Mappers.getMapper(ItemMapper.class);
    @Mapping(target = "price", expression = "java(new java.math.BigDecimal(item.price().doubleValue()))")
    @Mapping(target = "garment", source = "garment")
    ItemResponse mapToItemResponse(Item item);
    @Mapping(target = "garment.id", source = "garmentId")
    Item matToItem(ItemInsert itemDto);
    Item matToItem(ItemUpdate itemDto);
    @Mapping(target = "price", expression = "java(new java.math.BigDecimal(item.price().doubleValue()))")
    @Mapping(target = "garment", source = "garment")
    List<ItemResponse> mapToItemResponse(Collection<Item> items);
    @Mapping(target = "garment.id", source = "garmentId")
    Item matToItem(SaleItemCreateRequest saleItemCreateRequest);

}
