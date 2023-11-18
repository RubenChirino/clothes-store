package com.clothes.store.repository;

import com.clothes.store.domain.SaleCash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleCashRepository extends JpaRepository<SaleCash, Long> {
}
