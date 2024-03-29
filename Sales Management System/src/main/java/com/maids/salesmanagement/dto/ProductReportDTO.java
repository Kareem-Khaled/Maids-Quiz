package com.maids.salesmanagement.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductReportDTO {
	   	private long totalProducts;
	    private Map<String, Long> salesByProduct;
	    private double averagePrice;
	    private double highestPrice;
	    private double lowestPrice;
}
