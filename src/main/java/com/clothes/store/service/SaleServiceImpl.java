package com.clothes.store.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.clothes.store.domain.*;
import com.clothes.store.exceptions.BusinessException;
import com.clothes.store.repository.SaleCardRepository;
import com.clothes.store.repository.SaleCashRepository;
import com.clothes.store.repository.SaleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {
    private final Logger LOGGER = LoggerFactory.getLogger(SaleServiceImpl.class);
    private final SaleRepository saleRepository;
    private final SaleCashRepository saleCashRepository;
    private final SaleCardRepository saleCardRepository;
    private final ClientService clientService;
    private final GarmentService garmentService;
    private final ItemService itemService;

    @Autowired
    public SaleServiceImpl(final SaleRepository saleRepository,
                            final SaleCashRepository saleCashRepository,
                            final SaleCardRepository saleCardRepository,
                            final ClientService clientService,
                            final GarmentService garmentService,
                            final ItemService itemService) {
        this.saleRepository = saleRepository;
        this.saleCashRepository = saleCashRepository;
        this.saleCardRepository = saleCardRepository;
        this.clientService = clientService;
        this.garmentService = garmentService;
        this.itemService = itemService;
    }

    // Cash Methods
    @Override
    public SaleCash save(SaleCash sale) throws BusinessException {
        Client client = null;
        if (sale.getClient().getId() != null) {
            client = getClient(sale.getClient().getId());
        } else {
            throw new BusinessException("The client is required");
        }
        List<Item> items = new ArrayList<>();
        if (sale.getItems() != null) {
            items = getItems(sale.getItems());
        }
        sale = SaleCash.builder()
                .client(client)
                .date(Calendar.getInstance().getTime())
                .items(items)
                .build();
        return saleCashRepository.save(sale);
    }

    @Override
    public SaleCash save(SaleCash saleCash, Item item) throws BusinessException {
        saleCash.addItem(item);
        return saleCashRepository.save(saleCash);
    }

    // Card Methods
    @Override
    public SaleCard save(SaleCard sale) throws BusinessException {
        Client client = null;
        if (sale.getClient().getId() != null) {
            client = getClient(sale.getClient().getId());
        } else {
            throw new BusinessException("The client is required");
        }
        List<Item> items = new ArrayList<>();
        if (sale.getItems() != null) {
            items = getItems(sale.getItems());
        }
        sale = SaleCard.builder()
                .client(client)
                .date(Calendar.getInstance().getTime())
                .items(items)
                .installmentsQuantity(sale.getInstallmentsQuantity())
                .coefficientCard(new BigDecimal(0.01D))
                .build();
        return saleCardRepository.save(sale);
    }

    @Override
    public SaleCard save(SaleCard SaleCard, Item item) throws BusinessException {
        SaleCard.addItem(item);
        return saleCardRepository.save(SaleCard);
    }

    @Override
    public void delete(Sale sale) {
        saleRepository.delete(sale);
    }

    @Override
    public void delete(Long id) {
        saleRepository.deleteById(id);
    }

    @Override
    public Sale findById(Long id) throws BusinessException {
        LOGGER.debug("Search a sale by ID");
        Optional<Sale> itemOptional = saleRepository.findById(id);
        if (itemOptional.isPresent()) {
            return itemOptional.get();
        }
        throw new BusinessException("Not sale found by the ID: " + id);
    }

    @Override
    public List<Sale> list() {
        LOGGER.debug("Sale list");
        return saleRepository.findAll();
    }

    @Override
    public Page<Sale> list(Pageable pageable) {
        LOGGER.debug("Sale list by pages");
        LOGGER.debug("Pageable: offset: " + pageable.getOffset() + ", pageSize: " + pageable.getPageSize() + " and pageNumber: " + pageable.getPageNumber());
        return saleRepository.findAll(pageable);
    }

    @Override
    public long count() {
        return saleRepository.count();
    }

    @Override
    public Sale addItem(Long saleId, Item item) throws BusinessException {
        Sale sale = getSale(saleId);
        Garment garment = getGarment(item);
        Item newItem = Item.builder()
                .quantity(item.getQuantity())
                .garment(garment)
                .sale(sale)
                .build();
        newItem = itemService.save(newItem);
        sale.addItem(newItem);
        return sale;
    }

    @Override
    public Sale updateItem(Long saleId, Long itemId, Item item) throws BusinessException {
        Sale sale = getSale(saleId);
        Item actualItem = getItem(itemId);
        actualItem.setQuantity(item.getQuantity());
        actualItem = itemService.update(actualItem);
        return sale;
    }

    @Override
    public Sale deleteItem(Long saleId, Long itemId) throws BusinessException {
        Sale sale = getSale(saleId);
        Item actualItem = getItem(itemId);
        itemService.delete(itemId);
        sale.getItems().remove(actualItem);
        saleRepository.save(sale);
        return sale;
    }

    private Sale getSale(Long saleId) throws BusinessException {
        Optional<Sale> saleOptional = saleRepository.findById(saleId);
        if (saleOptional.isPresent()) {
            return saleOptional.get();
        } else {
            throw new BusinessException("Sale not found by the ID: " + saleId);
        }
    }

    private List<Item> getItems(List<Item> requestItems) throws BusinessException {
        List<Item> items = new ArrayList<>();
        for (Item requestItem : requestItems) {
            Garment garment = getGarment(requestItem);
            Item item = Item.builder()
                    .quantity(requestItem.getQuantity())
                    .garment(garment)
                    .build();
            items.add(item);
        }
        return items;
    }

    private Garment getGarment(Item requestItem) throws BusinessException {
        if (requestItem.getGarment().getId() != null) {
            return garmentService.findById(requestItem.getGarment().getId());
        } else {
            throw new BusinessException("The garment is required");
        }
    }

    private Item getItem(Long id) throws BusinessException {
        return itemService.findById(id);
    }

    private Client getClient(Long id) throws BusinessException {
        return clientService.findById(id);
    }
}
