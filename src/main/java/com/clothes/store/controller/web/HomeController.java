package com.clothes.store.controller.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.clothes.store.controller.StoreApp;
@Controller
public class HomeController extends StoreApp {
    private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    @GetMapping()
    public String viewHomePage(Model model) {
        LOGGER.info("GET - viewHomePage - /index");
        return "index";
    }
}
