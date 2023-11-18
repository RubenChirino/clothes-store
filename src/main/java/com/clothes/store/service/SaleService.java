package com.clothes.store.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.clothes.store.domain.Item;
import com.clothes.store.domain.Sale;
import com.clothes.store.domain.SaleCash;
import com.clothes.store.domain.SaleCard;
import com.clothes.store.exceptions.BusinessException;


public interface SaleService {
    // Cash Methods
    SaleCash save(SaleCash sale) throws BusinessException;
    SaleCash save(SaleCash sale, Item item) throws BusinessException;
    // Card Methods
    SaleCard save(SaleCard sale) throws BusinessException;
    SaleCard save(SaleCard sale, Item item) throws BusinessException;
    void delete(Sale sale);
    void delete(Long id);
    Sale findById(Long id) throws BusinessException;
    List<Sale> list();
    Page<Sale> list(Pageable pageable);
    long count();
    public Sale addItem(Long saleId, Item item) throws BusinessException;
    public Sale updateItem(Long saleId, Long itemId, Item item) throws BusinessException;
    public Sale deleteItem(Long saleId, Long itemId) throws BusinessException;

}
