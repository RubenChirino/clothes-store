package com.clothes.store.repository;

import com.clothes.store.domain.Garment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarmentRepository extends JpaRepository<Garment, Long> {

}
