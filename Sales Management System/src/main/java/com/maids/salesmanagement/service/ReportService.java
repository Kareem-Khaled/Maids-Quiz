package com.maids.salesmanagement.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import com.maids.salesmanagement.dto.ClientReportDTO;
import com.maids.salesmanagement.dto.ProductDTO;
import com.maids.salesmanagement.dto.ProductReportDTO;
import com.maids.salesmanagement.dto.SalesReportDTO;
import com.maids.salesmanagement.model.Product;
import com.maids.salesmanagement.model.Sale;
import com.maids.salesmanagement.model.SaleItem;
import com.maids.salesmanagement.model.Client;
import com.maids.salesmanagement.repository.ClientRepository;
import com.maids.salesmanagement.repository.ProductRepository;
import com.maids.salesmanagement.repository.SaleItemRepository;
import com.maids.salesmanagement.repository.SaleRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private SaleItemRepository saleItemRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    public SalesReportDTO generateSalesReportDTO(Date startDate, Date endDate) {
        List<Sale> filteredSales = saleRepository.findByCreationDateBetween(startDate, endDate);

        int totalSales = filteredSales.size();
        double totalRevenue = filteredSales.stream().mapToDouble(Sale::getTotal).sum();

        List<ProductDTO> topSellingProducts = getTopSellingProducts(filteredSales);

        return new SalesReportDTO(totalSales, totalRevenue, topSellingProducts);
    }

    private List<ProductDTO> getTopSellingProducts(List<Sale> sales) {
        Map<Product, Integer> productSalesCount = sales.stream()
                .flatMap(sale -> sale.getItems().stream())
                .collect(Collectors.groupingBy(SaleItem::getProduct, Collectors.summingInt(SaleItem::getQuantity)));

        OptionalInt maxSalesCount = productSalesCount.values().stream().mapToInt(Integer::intValue).max();

        return productSalesCount.entrySet().stream()
                .filter(entry -> entry.getValue() == maxSalesCount.orElse(0)) // Filter products with count equal to maxSalesCount
                .map(entry -> ProductToDTO(entry.getKey()))
                .collect(Collectors.toList());
    }

    private ProductDTO ProductToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
   
    //////////////////
    
    public ClientReportDTO generateClientReportDTO() {
        // Query Client data
        List<Client> Clients = clientRepository.findAll();

        int totalClients = Clients.size();

        Map<Long, Double> topSpendingClients = getTopSpendingClients();

//        Map<Client, Long> ClientActivity = null;

        Map<String, Long> ClientLocationStatistics = gatherClientLocationStatistics();

        return new ClientReportDTO(totalClients, topSpendingClients, ClientLocationStatistics);
    }

    private Map<String, Long> gatherClientLocationStatistics() {
        List<Object[]> locationCounts = clientRepository.countClientsByAddress();
        Map<String, Long> locationStatistics = new HashMap<>();
        
        for (Object[] result : locationCounts) {
            String location = (String) result[0];
            Long count = (Long) result[1];
            locationStatistics.put(location, count);
        }
        
        return locationStatistics;
    }
    
    public Map<Long, Double> getTopSpendingClients() {
        List<Sale> allSales = saleRepository.findAll();

        // Calculate spending for each client
        Map<Long, Double> spendingPerUser = allSales.stream()
            .collect(Collectors.groupingBy(Sale::getClientId, Collectors.summingDouble(Sale::getTotal)));

        // Sort users by spending
        spendingPerUser = spendingPerUser.entrySet().stream()
            .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return spendingPerUser;
    }
    
    
    //////////////////

    public ProductReportDTO generateProductReportDTO() {
        List<Product> allProducts = productRepository.findAll();

        // Inventory status
        long totalProducts = allProducts.size();

        // Sales performance
        Map<String, Long> salesByProduct = allProducts.stream()
            .collect(Collectors.toMap(Product::getName, this::calculateTotalSalesForProduct));

        // Pricing analysis (average price)
        double averagePrice = allProducts.stream()
            .mapToDouble(Product::getPrice)
            .average()
            .orElse(0.0);

        // Pricing analysis (highest price)
        double highestPrice = allProducts.stream()
            .mapToDouble(Product::getPrice)
            .max()
            .orElse(0.0);

        // Pricing analysis (lowest price)
        double lowestPrice = allProducts.stream()
            .mapToDouble(Product::getPrice)
            .min()
            .orElse(0.0);

        return new ProductReportDTO(totalProducts, salesByProduct, averagePrice, highestPrice, lowestPrice);
    }

    private Long calculateTotalSalesForProduct(Product product) {
        List<SaleItem> saleItems = saleItemRepository.findByProduct(product);
        return saleItems.stream().mapToLong(SaleItem::getQuantity).sum();
    }
}
