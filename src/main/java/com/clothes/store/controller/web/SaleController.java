package com.clothes.store.controller.web;

import com.clothes.store.Constants;

import com.clothes.store.controller.response.SaleCardResponse;
import com.clothes.store.controller.response.SaleCashResponse;
import com.clothes.store.controller.response.SaleResponse;
import com.clothes.store.controller.web.request.SaleCardCreateRequest;
import com.clothes.store.controller.web.request.SaleCashCreateRequest;
import com.clothes.store.controller.web.request.SaleItemCreateRequest;
import com.clothes.store.domain.Item;
import com.clothes.store.domain.Sale;
import com.clothes.store.domain.SaleCard;
import com.clothes.store.domain.SaleCash;
import com.clothes.store.exceptions.BusinessException;
import com.clothes.store.mapper.ItemMapper;
import com.clothes.store.mapper.SaleMapper;
import com.clothes.store.service.ItemService;
import com.clothes.store.service.SaleService;
import org.springframework.stereotype.Controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SaleController {
    private final Logger LOGGER = LoggerFactory.getLogger(SaleController.class);
    @Autowired
    private SaleService saleService;
    @Autowired
    private ItemService itemService;
    private final SaleMapper saleMapper = SaleMapper.instance;
    private final ItemMapper itemMapper = ItemMapper.instance;
    //    @Autowired
    //    private MapperFacade mapper;

    @GetMapping(path = "sales/list")
    public String showSalePage(Model model) {
        LOGGER.info("GET - showSalePage  - /sales/list");
        Pageable pageable = PageRequest.of(0, 20);
        Page<Sale> sales = saleService.list(pageable);
        LOGGER.info("GET - showSalePage sale final price: " + sales.getContent().toString());
        model.addAttribute("SalesList", sales);
        LOGGER.info("sales.size: " + sales.getNumberOfElements());
        return "sales/list_sales";
    }

    @GetMapping(path = "/sales/cash/new")
    public String showNewSaleCashPage(Model model) {
        LOGGER.info("GET - showNewSaleCashPage - /sales/cash/new");
        SaleCashCreateRequest sale = new SaleCashCreateRequest();
        Calendar calendar = Calendar.getInstance();
        Date toDay = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        String date = dateFormat.format(toDay);
        sale.setDate(date);
        model.addAttribute("sale", sale);
        LOGGER.info("sales: " + sale.toString());
        return "sales/new_sales_cash";
    }

    @GetMapping(path = "/sales/card/new")
    public String showNewSaleCardPage(Model model) {
        LOGGER.info("GET - showNewSaleCardPage - /sales/card/new");
        SaleCardCreateRequest sale = new SaleCardCreateRequest();
        Calendar calendar = Calendar.getInstance();
        Date toDay = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        String date = dateFormat.format(toDay);
        sale.setDate(date);
        model.addAttribute("sale", sale);
        LOGGER.info("sales: " + sale.toString());
        return "sales/new_sales_card";
    }

    @GetMapping(path = "/sales/{saleId}/item/new")
    public String showNewItemPage(Model model, @PathVariable(name = "saleId") Long saleId) {
        LOGGER.info("GET - showNewItemPage - /sale/" + saleId + "/item/new");
        SaleItemCreateRequest item = new SaleItemCreateRequest();
        item.setSaleId(saleId);
        model.addAttribute("item", item);
        LOGGER.info("item: " + item.toString());
        return "sales/new_sales_item";
    }

    @PostMapping(value = "/sales/cash/save")
    public String saveSaleCash(@ModelAttribute("sale") SaleCashCreateRequest saleData) {
        LOGGER.info("POST - saveSale - /sales/cash/save");
        LOGGER.info("saleData: " + saleData.toString());
        SaleCash sale = saleMapper.matToSaleCash(saleData);
        // SaleCash sale = mapper.map(saleData, SaleCash.class);
        // Save the new sale
        try {
            sale = saleService.save(sale);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/sales/list";
    }

    @PostMapping(value = "/sales/card/save")
    public String saveSaleCard(@ModelAttribute("sale") SaleCardCreateRequest saleData) {
        LOGGER.info("POST - saveSale - /sales/card/save");
        LOGGER.info("sale: " + saleData.toString());
        SaleCard sale = saleMapper.matToSaleCard(saleData);
        // SaleCard sale = mapper.map(saleData, SaleCard.class);
        // Save the new Sale
        try {
            sale = saleService.save(sale);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/sales/list";
    }

    @RequestMapping(value = "/sales/edit/{id}", method = RequestMethod.GET)
    public ModelAndView showEditSalePage(@PathVariable(name = "id") Long saleId) {
        LOGGER.info("GET - showEditSalePage - /sales/edit/{id}");
        LOGGER.info("sale: " + saleId);
        ModelAndView mav = new ModelAndView("sales/edit_sales");
        try {
            Sale sale = saleService.findById(saleId);
            mav.addObject("sale", sale);
        } catch (BusinessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mav;
    }

    @PostMapping(value = "/sales/item/save")
    public String saveSaleItem(@ModelAttribute("item") SaleItemCreateRequest saleItemData) {
        LOGGER.info("POST - saveSaleItem - sales/item/save");
        LOGGER.info("saleItemData: " + saleItemData.toString());
        Item item = itemMapper.matToItem(saleItemData);
        // Item item = mapper.map(saleItemData, Item.class);
        // Save the new sale
        Sale sale = null;
        try {
            sale = saleService.addItem(saleItemData.getSaleId(), item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Sale saved: " + sale.toString());
        LOGGER.info("redirect:/sales/edit/" + saleItemData.getSaleId().toString());
        return "redirect:/sales/show/" + saleItemData.getSaleId().toString();
    }

    @GetMapping(path = "/sales/show/{id}")
    public ModelAndView showSalePage(@PathVariable(name = "id") Long saleId) {
        LOGGER.info("GET - showSalePage  - /sales/show/{id}");
        LOGGER.info("sale: " + saleId);
        ModelAndView mav = new ModelAndView("sales/show_sales");
        Sale sale = null;
        try {
            sale = saleService.findById(saleId);
            mav.addObject("sale", sale);
        } catch (BusinessException e1) {
            LOGGER.error(e1.getMessage());
            e1.printStackTrace();
        }
        List<SaleResponse> salesToResponse = new ArrayList<SaleResponse>();
        // Convert Sale to SaleResponse
        try {
            if (sale instanceof SaleCash) {
                SaleCashResponse saleResponse = saleMapper.mapToSaleCashResponse((SaleCash) sale);
                // SaleCashResponse saleResponse = mapper.map((SaleCash) sale, SaleCashResponse.class);
                salesToResponse.add(saleResponse);
            } else if (sale instanceof SaleCard) {
                SaleCardResponse saleResponse = saleMapper.mapToSaleCardResponse((SaleCard) sale);
                // SaleCardResponse saleResponse = mapper.map((SaleCard) sale, SaleCardResponse.class);
                salesToResponse.add(saleResponse);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        mav.addObject("SalesList", salesToResponse);
        return mav;
    }

    @RequestMapping(value = "/sales/delete/{id}", method = RequestMethod.GET)
    public String deleteSale(@PathVariable(name = "id") Long saleId) {
        LOGGER.info("GET - deleteSale - /sales/delete/{id}");
        LOGGER.info("sale: " + saleId);
        saleService.delete(saleId);
        return "redirect:/sales/list";
    }

    @RequestMapping(value = "/item/delete/{id}", method = RequestMethod.GET)
    public String deleteItem(@PathVariable(name= "id")Long itemId){
        LOGGER.info("Get - deleteItem - /item/delete/{id}");
        LOGGER.info("item: " + itemId);
        itemService.delete(itemId);
        return "redirect:/sales/list";
    }
}
