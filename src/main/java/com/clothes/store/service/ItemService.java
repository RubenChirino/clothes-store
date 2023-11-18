package com.clothes.store.service;

import java.util.List;

import com.clothes.store.domain.Item;
import com.clothes.store.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface ItemService {
    Item save(Item item) throws BusinessException;
    Item update(Item item) throws BusinessException;
    public void delete(Item item);
    public void delete(Long id);
    public Item findById(Long id) throws BusinessException;
    public List<Item> listAll();
    public Page<Item> list(Pageable pageable);
    public long count();
}
