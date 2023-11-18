package com.clothes.store.controller.web;

import com.clothes.store.domain.Client;
import com.clothes.store.exceptions.BusinessException;
import com.clothes.store.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientController {

    private final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private ClientService clientService;

    @GetMapping(path = "/clients/list")
    public String showClientPage(Model model) {
        LOGGER.info("GET - showClientPage  - /clients/list");
        Pageable pageable = PageRequest.of(0, 20);
        Page<Client> clients = clientService.list(pageable);
        model.addAttribute("listClients", clients);
        LOGGER.info("clients.size: " + clients.getNumberOfElements());
        return "clients/list_clients";
    }

    @GetMapping(path = "/clients/new")
    public String showNewClientPage(Model model) {
        LOGGER.info("GET - showNewClientPage - /clients/new");
        Client client = new Client();
        model.addAttribute("client", client);
        LOGGER.info("clients: " + client.toString());
        return "clients/new_clients";
    }

    @PostMapping(value = "/clients/save")
    public String saveClient(@ModelAttribute("client") Client client) {
        LOGGER.info("POST - saveClient - /clients/save");
        LOGGER.info("client: " + client.toString());
        try {
            if (client.getId() == null) {
                clientService.save(client);
            } else {
                clientService.update(client);
            }
        } catch (BusinessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "redirect:/clients/list";
    }

    @RequestMapping(value = "/clients/edit/{id}", method = RequestMethod.GET)
    public ModelAndView showEditClientPage(@PathVariable(name = "id") Long clientId) {
        LOGGER.info("GET - showEditClientPage - /clients/edit/{id}");
        LOGGER.info("client: " + clientId);
        ModelAndView mav = new ModelAndView("clients/edit_clients");
        try {
            Client client = clientService.findById(clientId);
            mav.addObject("client", client);
        } catch (BusinessException e) {
            LOGGER.error("ERROR GETTING GARMENT");
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value = "/clients/delete/{id}", method = RequestMethod.GET)
    public String deleteClient(@PathVariable(name = "id") Long clientId) {
        LOGGER.info("GET - deleteClient - /clients/delete/{id}");
        LOGGER.info("client: " + clientId);
        clientService.delete(clientId);
        return "redirect:/clients/list";
    }


}
