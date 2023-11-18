package com.clothes.store.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class GarmentTest {

	@Test
	void testBuilder() {
		// Given
		Long id = 1L;
		String description = "Pants";
		GarmentType garmentType = GarmentType.PANTS;
		BigDecimal price = new BigDecimal(100);
		// When
 		Garment garment = Garment.builder()
 				.id(id)
 				.description(description)
 				.type(garmentType)
 				.basePrice(price)
 				.build();
		// Then
		assertNotNull(garment);
		assertEquals(id, garment.getId());
		assertEquals(description, garment.getDescription());
		assertEquals(garmentType, garment.getType());
		assertEquals(price, garment.getBasePrice());
	}

}
