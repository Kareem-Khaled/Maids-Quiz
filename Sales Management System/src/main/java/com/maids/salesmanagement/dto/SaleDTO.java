package com.maids.salesmanagement.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SaleDTO {
    private Long id;

    @NotNull
    private Long clientId;

    @NotNull
    private Long sellerId;

    private double total;

    private Date creationDate;

    @NotNull
    private List<SaleItemDTO> items;
}
