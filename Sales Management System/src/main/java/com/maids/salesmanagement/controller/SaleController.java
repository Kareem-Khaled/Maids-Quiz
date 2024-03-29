package com.maids.salesmanagement.controller;

import com.maids.salesmanagement.dto.SaleDTO;
import com.maids.salesmanagement.exception.ResourceNotFoundException;
import com.maids.salesmanagement.model.Sale;
import com.maids.salesmanagement.service.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sales")
@Validated // Enables method-level validation
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<SaleDTO> getAllSales() {
        List<Sale> sales = saleService.getAllSales();
        return sales.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SaleDTO getSaleById(@PathVariable Long id) {
        Sale sale = saleService.getSaleById(id);
        return convertToDTO(sale);
    }

    @PostMapping
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {
        SaleDTO createdSale = saleService.createSale(saleDTO);
        return ResponseEntity.ok().body(createdSale);
    }
    
    @PutMapping("/{id}")
    public SaleDTO updateSale(@PathVariable Long id, @Valid @RequestBody SaleDTO saleDTO) {
        Sale sale = convertToEntity(saleDTO);
        Sale updatedSale = saleService.updateSale(id, sale);
        return convertToDTO(updatedSale);
    }

    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
    }

    // Utility method to convert Sale entity to SaleDTO
    private SaleDTO convertToDTO(Sale sale) {
        SaleDTO saleDTO = modelMapper.map(sale, SaleDTO.class);
        saleDTO.setCreationDate(sale.getCreationDate());
        return saleDTO;
    }

    // Utility method to convert SaleDTO to Sale entity
    private Sale convertToEntity(SaleDTO saleDTO) {
        return modelMapper.map(saleDTO, Sale.class);
    }
}
   
