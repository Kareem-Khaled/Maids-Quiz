package com.maids.salesmanagement.service;

import com.maids.salesmanagement.dto.SaleDTO;
import com.maids.salesmanagement.dto.SaleItemDTO;
import com.maids.salesmanagement.exception.ResourceNotFoundException;
import com.maids.salesmanagement.model.Client;
import com.maids.salesmanagement.model.Product;
import com.maids.salesmanagement.model.Sale;
import com.maids.salesmanagement.model.SaleItem;
import com.maids.salesmanagement.repository.ClientRepository;
import com.maids.salesmanagement.repository.ProductRepository;
import com.maids.salesmanagement.repository.SaleItemRepository;
import com.maids.salesmanagement.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleItemRepository saleItemRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(Long saleId) {
        return saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + saleId));
    }

    public SaleDTO createSale(SaleDTO saleDTO) {
        Sale sale = new Sale();
        sale.setClientId(saleDTO.getClientId());
        sale.setSellerId(saleDTO.getSellerId());
        sale.setTotal(0); // Initialize total to zero

        validateSaleData(sale);
        
        // Save sale items first
        List<SaleItem> savedItems = new ArrayList<>();
        for (SaleItemDTO itemDTO : saleDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDTO.getProductId()));

            SaleItem saleItem = new SaleItem();
            saleItem.setProduct(product);
            saleItem.setQuantity(itemDTO.getQuantity());
            saleItem.setPrice(itemDTO.getPrice());
            saleItem.setSale(sale);

            // Save sale item
            savedItems.add(saleItem);
        }

        // Associate sale items with sale entity
        sale.setItems(savedItems);

        // Calculate total based on sale items
        double total = savedItems.stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();
        sale.setTotal(total);

        // Save sale entity
        Sale savedSale = saleRepository.save(sale);

        // Prepare and return DTO
        SaleDTO savedSaleDTO = new SaleDTO();
        savedSaleDTO.setId(savedSale.getId());
        savedSaleDTO.setClientId(savedSale.getClientId());
        savedSaleDTO.setSellerId(savedSale.getSellerId());
        savedSaleDTO.setTotal(savedSale.getTotal());
        savedSaleDTO.setCreationDate(savedSale.getCreationDate()); // Make sure to set creation date
        savedSaleDTO.setItems(savedItems.stream()
                .map(item -> new SaleItemDTO(item.getId(), item.getProduct().getId(), item.getQuantity(), item.getPrice()))
                .collect(Collectors.toList()));

        return savedSaleDTO;
    }
    
    private void validateSaleData(Sale sale) {
        clientRepository.findById(sale.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + sale.getClientId()));

        clientRepository.findById(sale.getSellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + sale.getSellerId()));
    }

    public Sale updateSale(Long saleId, Sale saleDetails) {
        Sale sale = getSaleById(saleId);
        
        // Update existing items and create new ones
        for (SaleItem newItem : saleDetails.getItems()) {
            Optional<SaleItem> existingItemOptional = sale.getItems().stream()
                    .filter(item -> item.getId().equals(newItem.getId()))
                    .findFirst();

            if (existingItemOptional.isPresent()) {
                // Update existing item with new values
                SaleItem existingItem = existingItemOptional.get();
                existingItem.setQuantity(newItem.getQuantity());
                existingItem.setPrice(newItem.getPrice());
            } else {
                // Create new item and add it to the sale
                Product product = productRepository.findById(newItem.getProduct().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + newItem.getProduct().getId()));

                SaleItem newSaleItem = new SaleItem();
                newSaleItem.setProduct(product);
                newSaleItem.setQuantity(newItem.getQuantity());
                newSaleItem.setPrice(newItem.getPrice());
                newSaleItem.setSale(sale);
                
                // Save and add the new item to the sale
                sale.getItems().add(newSaleItem);
            }
        }

        calculateTotal(sale);

        // Save the updated sale
        return saleRepository.save(sale);
    }



    public void deleteSale(Long saleId) {
        Sale sale = getSaleById(saleId);
        saleRepository.delete(sale);
    }

    private void calculateTotal(Sale sale) {
        double total = sale.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
        sale.setTotal(total);
    }
}
