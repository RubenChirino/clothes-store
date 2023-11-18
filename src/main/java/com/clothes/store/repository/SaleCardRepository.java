package com.clothes.store.repository;

import com.clothes.store.domain.SaleCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleCardRepository extends JpaRepository<SaleCard, Long> {
}
