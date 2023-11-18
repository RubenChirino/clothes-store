package com.clothes.store.service;

import com.clothes.store.domain.Client;
import com.clothes.store.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ClientService  {
    Client save(final Client client) throws BusinessException;
    Client update(final Client client) throws BusinessException;
    void delete(final Client client);
    void delete(final Long id);
    Client findById(final Long id) throws BusinessException;
    List<Client> list();
    Page<Client> list(Pageable pageable);
    long count();
}
