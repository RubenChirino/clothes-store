package com.clothes.store.controller.rest;

import java.util.List;
import java.util.Objects;

import com.clothes.store.controller.StoreAppRest;
import com.clothes.store.controller.request.ClientInsert;
import com.clothes.store.controller.request.ClientUpdate;
import com.clothes.store.controller.response.ClientResponse;
import com.clothes.store.domain.Client;
import com.clothes.store.exceptions.BusinessException;
import com.clothes.store.mapper.ClientMapper;
import com.clothes.store.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientControllerRest extends StoreAppRest {
    private final Logger LOGGER = LoggerFactory.getLogger(ClientControllerRest.class);
    @Autowired
    private ClientService service;
    private final ClientMapper mapper = ClientMapper.instance;
    // @Autowired
    // private MapperFacade mapper;

    /**
     * List
     */
    @GetMapping(path = "/clients/all")
    public List<Client> getListAll() {
        LOGGER.info("List all the clients");
        return service.list();
    }
    /**
     * Paginate List
     */
    @GetMapping(path = "/clients")
    public ResponseEntity<Page<ClientResponse>> getList(Pageable pageable) {
        LOGGER.info("List all the paginates clients");
        LOGGER.info("Pageable: " + pageable);
        Page<ClientResponse> clientResponse = null;
        Page<Client> clients = null;
        try {
            clients = service.list(pageable);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        try {
            clientResponse = clients.map(mapper::mapToClientResponse);
            // clientResponse = clients.map(client -> mapper.map(client, ClientResponse.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(clientResponse, HttpStatus.OK);
    }

    /**
     * Search client by id
     * @param id client ID
     * @return return the client
     */
    @GetMapping(path = "/clients/{id}")
    public ResponseEntity<Object> getClient(@PathVariable Long id) {
        LOGGER.info("List the requested client");
        ClientResponse clientResponse = null;
        Client client = null;
        try {
            client = service.findById(id);
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        try {
            clientResponse = mapper.mapToClientResponse(client);
            // clientResponse = mapper.map(client, ClientResponse.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(clientResponse, HttpStatus.OK);
    }

    /**
     * Save the new client
     *
     * @param clientData new client data
     * @return a new client
     */
    @PostMapping(path = "/clients")
    public ResponseEntity<ClientResponse> createClient(@RequestBody ClientInsert clientData) {
        Client client = null;
        ClientResponse clientResponse = null;
        // Convert ClientInsertRequest to Client
        try {
            client = mapper.matToClient(clientData);
            // client = mapper.map(clientData, Client.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        // Save the new Client
        try {
            client = service.save(client);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
        // Convert the Client to ClientResponse
        try {
            clientResponse = mapper.mapToClientResponse(client);
            // clientResponse = mapper.map(client, ClientResponse.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(clientResponse, HttpStatus.CREATED);
    }

    /**
     * Modify the data for a client
     *
     * @param id client ID
     * @param clientData client data
     * @return the data from a modified client
     */
    @PutMapping("/clients/{id}")
    public ResponseEntity<Object> updateClient(@PathVariable("id") long id,
                                                @RequestBody ClientUpdate clientData) {
        Client clientToModify = null;
        Client newClient = null;
        ClientResponse clientResponse = null;
        // Convert the ClientInsertRequest to Client
        try {
            newClient = mapper.matToClient(clientData);
            // newClient = mapper.map(clientData, Client.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        try {
            clientToModify = service.findById(id);
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        if (Objects.nonNull(clientToModify)) {
            clientToModify.setName(newClient.getName());
            clientToModify.setLastName(newClient.getLastName());
            // Save the new Client in Client to modify
            try {
                clientToModify = service.update(clientToModify);
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
            LOGGER.error("Client to modify is null");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        // Convert Client to ClientResponse
        try {
            clientResponse = mapper.mapToClientResponse(clientToModify);
            // clientResponse = mapper.map(clientToModify, ClientResponse.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(clientResponse, HttpStatus.CREATED);
    }

    /**
     * Delete a client
     * @param id client iD
     * @return
     */
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
