package com.clothes.store.service;

import com.clothes.store.domain.Client;
import com.clothes.store.exceptions.BusinessException;
import com.clothes.store.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    private final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository repository;
    @Autowired
    public ClientServiceImpl(final ClientRepository repository) {
        this.repository = repository;
    }
    @Override
    public Client save(final Client client) throws BusinessException {
        LOGGER.debug("Save the client: " + client.toString());
        if (client.getId() == null) {
            return repository.save(client);
        }
        throw new BusinessException("We cannot create a client with a specific id");
    }
    @Override
    public Client update(final Client client) throws BusinessException {
        LOGGER.debug("Modify the client: " + client.toString());
        if (client.getId() != null) {
            return repository.save(client);
        }
        throw new BusinessException("We cannot modify a client that not exists");
    }
    @Override
    public void delete(final Client client) {
        LOGGER.debug("Delete the client: " + client.toString());
        repository.delete(client);
    }
    public void delete(final Long id) {
        LOGGER.debug("Delete the client by id: " + id);
        repository.deleteById(id);
    }
    @Override
    public Client findById(final Long id) throws BusinessException {
        LOGGER.debug("Search the client by id: " + id);
        Optional<Client> clientOptional = repository.findById(id);
        if (clientOptional.isPresent()) {
            return clientOptional.get();
        }
        throw new BusinessException("We cannot find a client by the id: " + id);
    }
    @Override
    public List<Client> list() {
        LOGGER.debug("Clients list");
        return repository.findAll();
    }
    @Override
    public Page<Client> list(Pageable pageable) {
        LOGGER.debug("Clients list by page");
        LOGGER.debug("Pageable: offset: " + pageable.getOffset() + ", pageSize: " + pageable.getPageSize() + " and pageNumber: " + pageable.getPageNumber());
        return repository.findAll(pageable);
    }
    @Override
    public long count() {
        return repository.count();
    }

}
