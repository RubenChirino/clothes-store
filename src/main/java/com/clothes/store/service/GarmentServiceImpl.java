package com.clothes.store.service;

import java.util.List;
import java.util.Optional;

import com.clothes.store.domain.Garment;
import com.clothes.store.domain.GarmentType;
import com.clothes.store.exceptions.BusinessException;
import com.clothes.store.repository.GarmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GarmentServiceImpl implements GarmentService {
    private final Logger LOGGER = LoggerFactory.getLogger(GarmentServiceImpl.class);
    private final GarmentRepository repository;
    @Autowired
    public GarmentServiceImpl(final GarmentRepository repository) {
        this.repository = repository;
    }
    @Override
    public Garment save(final Garment garment) throws BusinessException {
        LOGGER.debug("Save the garment: " + garment.toString());
        if (garment.getId() == null) {
            return repository.save(garment);
        }
        throw new BusinessException("We cannot create a garment with a specific ID");
    }
    @Override
    public Garment update(final Garment garment) throws BusinessException {
        LOGGER.debug("Update the garment: " + garment.toString());
        if (garment.getId() != null) {
            return repository.save(garment);
        }
        throw new BusinessException("We cannot modify a garment that is not created yet");
    }
    @Override
    public void delete(final Garment garment) {
        LOGGER.debug("Delete the garment: " + garment.toString());
        repository.delete(garment);
    }
    public void delete(final Long id) {
        LOGGER.debug("Delete the garment with the ID: " + id);
        repository.deleteById(id);
    }
    @Override
    public Garment findById(final Long id) throws BusinessException {
        LOGGER.debug("Find the garment with the ID: " + id);
        Optional<Garment> garmentOptional = repository.findById(id);
        if (garmentOptional.isPresent()) {
            return garmentOptional.get();
        }
        throw new BusinessException("Cannot find the garment with the ID: " + id);
    }
    @Override
    public List<Garment> list() {
        LOGGER.debug("Garments list");
        return repository.findAll();
    }
    @Override
    public Page<Garment> list(Pageable pageable) {
        LOGGER.debug("Garment list by page");
        LOGGER.debug("Pageable: offset: " + pageable.getOffset() + ", pageSize: " + pageable.getPageSize() + " and pageNumber: " + pageable.getPageNumber());
        return repository.findAll(pageable);
    }
    @Override
    public long count() {
        return repository.count();
    }
    @Override
    public List<GarmentType> getGarmentType() {
        return GarmentType.getGarmentType();
    }
}
