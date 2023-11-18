package com.clothes.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.clothes.store.controller.request.GarmentInsert;
import com.clothes.store.controller.request.GarmentUpdate;
import com.clothes.store.controller.response.GarmentResponse;
import com.clothes.store.domain.Garment;

@Mapper
public interface GarmentMapper {
    GarmentMapper instance = Mappers.getMapper(GarmentMapper.class);
    GarmentResponse mapToGarmentResponse(Garment garment);
    Garment mapToGarment(GarmentInsert garmentDto);
    Garment mapToGarment(GarmentUpdate garmentDto);
}
