package com.clothes.store.controller.rest;

import java.util.List;
import java.util.Objects;

import com.clothes.store.controller.request.GarmentInsert;
import com.clothes.store.controller.request.GarmentUpdate;
import com.clothes.store.controller.response.GarmentResponse;
import com.clothes.store.domain.Garment;
import com.clothes.store.exceptions.BusinessException;
import com.clothes.store.mapper.GarmentMapper;
import com.clothes.store.service.GarmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.clothes.store.controller.StoreAppRest;

@RestController
public class GarmentControllerRest extends StoreAppRest {

    private final Logger LOGGER = LoggerFactory.getLogger(GarmentControllerRest.class);
    @Autowired
    private GarmentService service;
    private final GarmentMapper mapper = GarmentMapper.instance;

    /**
     * List Garments
     */
    @GetMapping(path = "/garments/all")
    public List<Garment> getList(){
        LOGGER.info("Garments List from API");
        return service.list();
    }

    @GetMapping(path = "/garments")
    public ResponseEntity<Page<GarmentResponse>> getList(Pageable pageable) {
        LOGGER.info("List Garments by Pages");
        LOGGER.info("Pageable: " + pageable);
        Page<GarmentResponse> garmentResponse = null;
        Page<Garment> garments = null;
        try {
            garments = service.list(pageable);
        } catch (Exception e) {
            LOGGER.error("Error: " +  e.getMessage());
            e.printStackTrace();
        }
        try {
            // garmentResponse = garments.map(garment -> mapper.map(garment, GarmentResponse.class));
            LOGGER.info(">>>>> garments >>>> " + garments.toString());
            showGarments(garments);
            garmentResponse = garments.map(mapper::mapToGarmentResponse);
            showGarmentResponse(garmentResponse);
            LOGGER.info(">>>>> garmentResponse >>>> " + garmentResponse.toString());
        } catch (Exception e) {
            LOGGER.error("Error: " +  e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(garmentResponse, HttpStatus.OK);
    }

    private void showGarmentResponse(Page<GarmentResponse> garmentResponse) {
        System.out.println(garmentResponse.getContent().toString());
    }

    private void showGarments(Page<Garment> garments) {
        System.out.println(garments.getContent().toString());
    }

    /**
     * Search Garment By ID
     * @param id Garment ID
     * @return Return the Garment
     */
    @GetMapping(path = "/garments/{id}")
    public ResponseEntity<Object> getGarment(@PathVariable Long id) {
        LOGGER.info("List the garment found");
        GarmentResponse garmentResponse = null;
        Garment garment = null;
        try {
            garment = service.findById(id);
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        try {
            // garmentResponse = mapper.map(garment, GarmentResponse.class);
            garmentResponse = mapper.mapToGarmentResponse(garment);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(garmentResponse, HttpStatus.OK);
    }
    /**
     * Save a new Garment
     *
     * @param garmentData Dara for the new garment
     * @return A new garment
     */
    @PostMapping(path = "/garments")
    public ResponseEntity<GarmentResponse> createGarment(@RequestBody GarmentInsert garmentData) {
        Garment garment = null;
        GarmentResponse garmentResponse = null;
        // Covert GarmentInsert in Garment
        try {
            // garment = mapper.map(garmentData, Garment.class);
            garment = mapper.mapToGarment(garmentData);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        // Save the new Garment
        try {
            garment = service.save(garment);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
        // Convert Garment in GarmentResponse
        try {
            // garmentResponse = mapper.map(garment, GarmentResponse.class);
            garmentResponse = mapper.mapToGarmentResponse(garment);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(garmentResponse, HttpStatus.CREATED);
    }

    /**
     * Edit a Garment
     *
     * @param id Garment ID
     * @param garmentData Data to modify
     * @return The data modified
     */
    @PutMapping("/garments/{id}")
    public ResponseEntity<Object> updateGarment(@PathVariable("id") long id,
                                               @RequestBody GarmentUpdate garmentData) {
        Garment garmentToModify = null;
        Garment newGarment = null;
        GarmentResponse garmentResponse = null;
        // Convert GarmentInsert in Garment
        try {
            // newGarment = mapper.map(garmentData, Garment.class);
            newGarment = mapper.mapToGarment(garmentData);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        try {
            garmentToModify = service.findById(id);
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        if (Objects.nonNull(garmentToModify)) {
            garmentToModify.setDescription(newGarment.getDescription());
            garmentToModify.setType(newGarment.getType());
            garmentToModify.setBasePrice(newGarment.getBasePrice());

            // Save the new Garment in Garment to Modify
            try {
                garmentToModify = service.update(garmentToModify);
            } catch (BusinessException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
        } else {
            LOGGER.error("The Garment to modify is null");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        // Convert Garment in GarmentResponse
        try {
            // garmentResponse = mapper.map(garmentToModify, GarmentResponse.class);
            garmentResponse = mapper.mapToGarmentResponse(garmentToModify);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(garmentResponse, HttpStatus.CREATED);
    }
    /**
     * Delete the Garment
     * @param id Garment ID
     * @return
     */
    @DeleteMapping("/garments/{id}")
    public ResponseEntity<HttpStatus> deleteGarment(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
