package com.clothes.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public abstract class StoreAppRest {
    // http://localhost:8090/api
    // The port 8090, is setting iin the server.port property
}
