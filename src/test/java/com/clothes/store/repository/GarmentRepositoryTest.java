package com.clothes.store.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.clothes.store.domain.Garment;
import com.clothes.store.domain.GarmentType;

import org.junit.jupiter.api.Test;
@SpringBootTest
@ExtendWith(SpringExtension.class)
class GarmentRepositoryTest {
	
	private final Logger LOGGER = LoggerFactory.getLogger(GarmentRepositoryTest.class);

	@Autowired
	private GarmentRepository prendaRepository;
	
	@Test
	void testFindAll() {
		assertNotNull(prendaRepository, "El repositorio es nulo.");
		List<Garment> prendas = prendaRepository.findAll();
		LOGGER.info("Prendas encontradas: " + prendas.size());
		assertNotNull(prendas, "prendas es nulo");
		assertTrue(prendas.size() > 0, "No existen prendas.");
	}
	@Test
	void testFindById() {
		Long id = 4L;
		Garment prenda = null;
		Optional<Garment> prendaOptional = prendaRepository.findById(id);
		if (prendaOptional.isPresent()) {
			prenda = prendaOptional.get();
			LOGGER.info("Prenda encontrada para el id: " + prenda.getId());
			LOGGER.info("Prenda Tipo: " + prenda.getType());
			LOGGER.info("Prenda Tipo.Descripcion: " + prenda.getType().getDescription());
			assertEquals(GarmentType.PANTS, prenda.getType());
		} else {
			LOGGER.info("Prenda no encontrada para el id: " + id);
			prenda = prendaOptional.get();
			assertNull(prenda);
		}
	}

}
