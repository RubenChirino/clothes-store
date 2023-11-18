package com.clothes.store.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.clothes.store.domain.Garment;
import com.clothes.store.domain.GarmentType;
import com.clothes.store.exceptions.BusinessException;

public interface GarmentService {
    Garment save(final Garment garment) throws BusinessException;
    Garment update(final Garment garment) throws BusinessException;
    void delete(final Garment garment);
    void delete(final Long id);
    Garment findById(final Long id) throws BusinessException;
    List<Garment> list();
    Page<Garment> list(Pageable pageable);
    long count();
    List<GarmentType> getGarmentType();

}

