package com.maids.salesmanagement.controller;

import com.maids.salesmanagement.dto.ClientReportDTO;
import com.maids.salesmanagement.dto.ProductReportDTO;
import com.maids.salesmanagement.dto.SalesReportDTO;
import com.maids.salesmanagement.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/sales")
    public ResponseEntity<SalesReportDTO> generateSalesReportDTO(@RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                                                                  @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Ensure endDate is not after the current date and time
        endDate = endDate.compareTo(currentDate) > 0 ? currentDate : endDate;

        // Set endDate to the end of the day
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        endDate = calendar.getTime();
   
    	SalesReportDTO report = reportService.generateSalesReportDTO(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/clients")
    public ResponseEntity<ClientReportDTO> generateClientReportDTO() {
        ClientReportDTO report = reportService.generateClientReportDTO();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/products")
    public ResponseEntity<ProductReportDTO> generateProductReportDTO() {
        ProductReportDTO report = reportService.generateProductReportDTO();
        return ResponseEntity.ok(report);
    }
}
