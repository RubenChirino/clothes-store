package com.clothes.store.controller.rest;

import java.util.List;

import com.clothes.store.controller.StoreAppRest;
import com.clothes.store.controller.request.ItemInsert;
import com.clothes.store.controller.request.ItemUpdate;
import com.clothes.store.controller.request.SaleCardRequest;
import com.clothes.store.controller.request.SaleCashRequest;
import com.clothes.store.controller.response.SaleResponse;
import com.clothes.store.domain.Item;
import com.clothes.store.domain.Sale;
import com.clothes.store.domain.SaleCard;
import com.clothes.store.domain.SaleCash;
import com.clothes.store.mapper.ItemMapper;
import com.clothes.store.mapper.SaleMapper;
import com.clothes.store.service.SaleService;
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
public class SaleControllerRest extends StoreAppRest {
    private final Logger LOGGER = LoggerFactory.getLogger(SaleControllerRest.class);
    @Autowired
    private SaleService saleService;
    private final SaleMapper saleMapper = SaleMapper.instance;
    private final ItemMapper itemMapper = ItemMapper.instance;
    // @Autowired
    //private MapperFacade mapper;

    /**
     * List
     */
    @GetMapping(path = "/sales/all")
    public List<Sale> getListAll() {
        LOGGER.info("List all the sales");
        return saleService.list();
    }

    /**
     * List paginate
     */
    @SuppressWarnings("unused")
	@GetMapping(path = "/sales")
    public ResponseEntity<Page<Sale>> getList(Pageable pageable) {
        // public ResponseEntity<Page<SaleResponse>> getList(Pageable pageable) {
        LOGGER.info("List all the paginates sales");
        LOGGER.info("Pageable: " + pageable);
        Page<SaleResponse> saleResponse = null;
        Page<Sale> sales = null;
        try {
            sales = saleService.list(pageable);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        try {
            // saleResponse = sales.map(sale -> mapper.map(sale, saleResponse.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(sales, HttpStatus.CREATED);
        // return new ResponseEntity<>(saleResponse, HttpStatus.CREATED);
    }

    /**
     * Search sale by id
     * @param saleId sale ID
     * @return return the sale
     */
    @GetMapping(path = "/sales/{saleId}")
    public ResponseEntity<Object> getSale(@PathVariable Long saleId) {
        LOGGER.info("list to the requested sale");
        SaleResponse saleResponse = null;
        Sale sale = null;
        try {
            sale = saleService.findById(saleId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        try {
            if (sale instanceof SaleCash) {
                saleResponse = saleMapper.mapToSaleCashResponse((SaleCash) sale);
            } else if (sale instanceof SaleCard) {
                saleResponse = saleMapper.mapToSaleCardResponse((SaleCard) sale);
            }
            // saleResponse = mapper.map(sale, SaleResponse.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(saleResponse, HttpStatus.CREATED);
    }

    /**
     * Save a Cash Sale
     *
     * @param saleData data for the new sale
     * @return new sale
     */
    @PostMapping(path = "/sales/cash")
    public ResponseEntity<SaleResponse> createSale(@RequestBody SaleCashRequest saleData) {
        SaleResponse saleResponse = null;
        SaleCash sale = saleMapper.matToSaleCash(saleData);
        // SaleCash sale = mapper.map(saleData, SaleCash.class);
        return saveSale(sale, saleResponse);
    }

    /**
     * Save a new card sale
     *
     * @param saleData new card sale data
     * @return a new sale
     */
    @PostMapping(path = "/sales/card")
    public ResponseEntity<SaleResponse> createSale(@RequestBody SaleCardRequest saleData) {
        SaleResponse saleResponse = null;
        SaleCard sale = saleMapper.matToSaleCard(saleData);
        // SaleCard sale = mapper.map(saleData, SaleCard.class);
        return saveSale(sale, saleResponse);
    }

    /**
     * Save a Sale
     * @param sale
     * @param saleResponse
     * @return
     */
    private ResponseEntity<SaleResponse> saveSale(SaleCash sale, SaleResponse saleResponse) {
        // Save the new Sale
        try {
            sale = saleService.save(sale);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
        // Convert the Sale tp SaleResponse
        try {
            saleResponse = saleMapper.mapToSaleCashResponse(sale);
            // saleResponse = mapper.map(sale, saleResponse.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(saleResponse, HttpStatus.CREATED);
    }

    /**
     *  Save a Sale
     * @param sale
     * @param saleResponse
     * @return
     */
    private ResponseEntity<SaleResponse> saveSale(SaleCard sale, SaleResponse saleResponse) {
        // Save the new Sale
        try {
            sale = saleService.save(sale);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
        // Convert the Sale in SaleResponse
        try {
            saleResponse = saleMapper.mapToSaleCardResponse(sale);
            // saleResponse = mapper.map(sale, SaleResponse.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(saleResponse, HttpStatus.CREATED);
    }

    /**
     * Save the new item into the sale
     *
     * @param saleId sale ID
     * @param itemData new Item data
     * @return a modified sale
     */
    @PostMapping(path = "/sales/{saleId}/items")
    public ResponseEntity<SaleResponse> createItem(@PathVariable("saleId") long saleId,
                                                    @RequestBody ItemInsert itemData) {
        SaleResponse saleResponse = null;
        Item item = null;
        try {
            item = itemMapper.matToItem(itemData);
            // item = mapper.map(itemData, Item.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        Sale sale = null;
        try {
            sale = saleService.addItem(saleId, item);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        // Convert the Sale in SaleResponse
        try {
            if (sale instanceof SaleCash) {
                saleResponse = saleMapper.mapToSaleCashResponse((SaleCash) sale);
                // saleResponse = mapper.map((SaleCash) sale, SaleResponse.class);
            } else if (sale instanceof SaleCard) {
                saleResponse = saleMapper.mapToSaleCardResponse((SaleCard) sale);
                // saleResponse = mapper.map((SaleCard) sale, SaleResponse.class);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(saleResponse, HttpStatus.CREATED);
    }

    /**
     * Modify a sale item
     *
     * @param saleId sale ID
     * @param itemId item ID
     * @param itemData Item data to modify
     * @return a modified sale
     */
    @PutMapping(path = "/sales/{saleId}/items/{itemId}")
    public ResponseEntity<SaleResponse> modifyItem(@PathVariable("saleId") long saleId,
                                                    @PathVariable("itemId") long itemId,
                                                    @RequestBody ItemUpdate itemData) {
        SaleResponse saleResponse = null;
        Item item = null;
        try {
            item = itemMapper.matToItem(itemData);
            // item = mapper.map(itemData, Item.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            // e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
        Sale sale = null;
        try {
            sale = saleService.updateItem(saleId, itemId, item);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            //e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        // Convert the Sale in SaleResponse
        try {
            if (sale instanceof SaleCash) {
                saleResponse = saleMapper.mapToSaleCashResponse((SaleCash) sale);
                // saleResponse = mapper.map((SaleCash) sale, SaleResponse.class);
            } else if (sale instanceof SaleCard) {
                saleResponse = saleMapper.mapToSaleCardResponse((SaleCard) sale);
                // saleResponse = mapper.map((SaleCard) sale, SaleResponse.class);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(saleResponse, HttpStatus.CREATED);
    }

    /**
     * Dale a sale item
     * @param saleId Sale ID
     * @param itemId Item ID
     * @return
     */
    @DeleteMapping("/sales/{saleId}/items/{itemId}")
    public ResponseEntity<SaleResponse> deleteClient(@PathVariable("saleId") long saleId,
                                                       @PathVariable("itemId") long itemId) {
        SaleResponse saleResponse = null;
        Sale sale = null;
        try {
            sale = saleService.deleteItem(saleId, itemId);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
        // Convert the Sale in SaleResponse
        try {
            if (sale instanceof SaleCash) {
                saleResponse = saleMapper.mapToSaleCashResponse((SaleCash) sale);
                // saleResponse = mapper.map((SaleCash) sale, SaleResponse.class);
            } else if (sale instanceof SaleCard) {
                saleResponse = saleMapper.mapToSaleCardResponse((SaleCard) sale);
                // saleResponse = mapper.map((SaleCard) sale, SaleResponse.class);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(saleResponse, HttpStatus.CREATED);
    }

}
