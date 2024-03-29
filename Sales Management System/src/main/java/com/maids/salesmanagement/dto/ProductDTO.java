package com.maids.salesmanagement.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.*;
import java.util.Date;

@Data
public class ProductDTO {
    private Long id;
    
    @NotNull
    private String name;
    
    @Size(min = 10)
    private String description;

    @Positive
    private Double price;
    
    @Positive
    private int availableQuantity;
    
    private Date creationDate;
    private Long categoryId; 

}
