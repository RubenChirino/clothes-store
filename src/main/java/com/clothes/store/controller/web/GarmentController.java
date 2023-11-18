package com.clothes.store.controller.web;

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
import com.clothes.store.domain.Garment;
import com.clothes.store.exceptions.BusinessException;
import com.clothes.store.service.GarmentService;

@Controller
public class GarmentController {

    private final Logger LOGGER = LoggerFactory.getLogger(GarmentController.class);

    @Autowired
    private GarmentService garmentService;

    @GetMapping(path = "/garments/list")
    public String showGarmentPage(Model model) {
        LOGGER.info("GET - showGarmentPage  - /garments/list");

        Pageable pageable = PageRequest.of(0, 20);
        Page<Garment> garments = garmentService.list(pageable);
        model.addAttribute("GarmentsList", garments);
        model.addAttribute("pageNumber", garments.getPageable().getPageNumber());
        model.addAttribute("totalPages", garments.getTotalPages());

        LOGGER.info("garments.size: " + garments.getNumberOfElements());
        return "garments/list_garments";
    }

    @GetMapping(path = "/garments/new")
    public String showNewGarmentPage(Model model) {
        LOGGER.info("GET - showNewGarmentPage - /garments/new");
        Garment garment = new Garment();
        model.addAttribute("garment", garment);
        model.addAttribute("garmentType", garmentService.getGarmentType());

        LOGGER.info("garments: " + garment.toString());

        return "garments/new_garments";
    }

    @PostMapping(value = "/garments/save")
    public String saveGarment(@ModelAttribute("garment") Garment garment) {
        LOGGER.info("POST - saveGarment - /garments/save");
        LOGGER.info("garment: " + garment.toString());
        try {
            if (garment.getId() == null) {
                garmentService.save(garment);
            } else {
                garmentService.update(garment);
            }
        } catch (BusinessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "redirect:/garments/list";
    }

    @RequestMapping(value = "/garments/edit/{id}", method = RequestMethod.GET)
    public ModelAndView showEditGarmentPage(@PathVariable(name = "id") Long garmentId) {
        LOGGER.info("GET - showEditGarmentPage - /garments/edit/{id}");
        LOGGER.info("garment: " + garmentId);

        ModelAndView mav = new ModelAndView("garments/edit_garments");

        Garment garment = null;
        try {

            garment = garmentService.findById(garmentId);

            mav.addObject("garment", garment);
            mav.addObject("garmentTypeActual", garment.getType());

        } catch (BusinessException e) {
            LOGGER.error("ERROR GETTING THE GARMENT");
            e.printStackTrace();
        }

        mav.addObject("garmentType", garmentService.getGarmentType());
        return mav;
    }

    @RequestMapping(value = "/garments/delete/{id}", method = RequestMethod.GET)
    public String deleteGarment(@PathVariable(name = "id") Long garmentId) {
        LOGGER.info("GET - deleteGarment - /garments/delete/{id}");
        LOGGER.info("garment: " + garmentId);
        garmentService.delete(garmentId);
        return "redirect:/garments/list";
    }


}
