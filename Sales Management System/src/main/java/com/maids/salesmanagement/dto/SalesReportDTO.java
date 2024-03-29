package com.maids.salesmanagement.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesReportDTO {
    private int totalSales;
    private double totalRevenue;
    private List<ProductDTO> topSellingProducts;

}