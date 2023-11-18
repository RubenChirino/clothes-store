package com.clothes.store.service;

import static org.junit.jupiter.api.Assertions.*;

import com.clothes.store.domain.Garment;
import com.clothes.store.domain.GarmentType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
class GarmentServiceTest {

	private final Logger LOGGER = LoggerFactory.getLogger(GarmentServiceTest.class);
	@Autowired
	private GarmentService garmentService;
	@Test
	void testList() {
		assertNotNull(garmentService, "The service is null");
		List<Garment> garments = garmentService.list();
		LOGGER.info("Garments found: " + garments.size());
		assertNotNull(garments, "Garments is null");
		assertTrue(garments.size() > 0, "No Garments exists");
	}
	@Test
	void testTipoPrenda() {
		List<GarmentType> list = garmentService.getGarmentType();
		LOGGER.info("Garment types found: " + list.size());
		assertNotNull(list, "garment type is null");
		assertTrue(list.size() == 8, "The types quantity is different");
	}

//	private final PrendaMapper mapper = PrendaMapper.instance;
//
//	@Test
//	void testMapToPrendaResponse() {
//		Prenda prenda = Prenda.builder()
//				.id(10L)
//				.descripcion("prueba dto")
//				.tipo(TipoPrenda.BUFANDA)
//				.precioBase(new BigDecimal(100.2))
//				.build();
//		LOGGER.info("Prenda: " + prenda.toString());
//
//		PrendaResponse prendaResponse = mapper.mapToPrendaResponse(prenda);
//		LOGGER.info("prendaResponse: " + prendaResponse.toString());
//	}
//
//	@Test
//	void testPrendaInsertRequestMatToPrenda() {
//		PrendaInsertRequest prendaInsertRequest = PrendaInsertRequest.builder()
//				.descripcion("prueba dto")
//				.tipo("BUFANDA")
//				.precioBase(new BigDecimal(100.2))
//				.build();
//		LOGGER.info("prendaInsertRequest: " + prendaInsertRequest.toString());
//
//		Prenda prenda = mapper.matToPrenda(prendaInsertRequest);
//		LOGGER.info("Prenda: " + prenda.toString());
//	}
//
//	@Test
//	void testPrendaUpdateRequestMatToPrenda() {
//		PrendaUpdateRequest prendaUpdateRequest = PrendaUpdateRequest.builder()
//				.descripcion("prueba dto")
//				.tipo("BUFANDA")
//				.precioBase(new BigDecimal(100.2))
//				.build();
//		LOGGER.info("prendaUpdateRequest: " + prendaUpdateRequest.toString());
//
//		Prenda prenda = mapper.matToPrenda(prendaUpdateRequest);
//		LOGGER.info("Prenda: " + prenda.toString());
//	}

}
