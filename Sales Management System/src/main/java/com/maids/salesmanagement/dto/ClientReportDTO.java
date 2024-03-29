package com.maids.salesmanagement.dto;

import java.util.List;
import java.util.Map;

import com.maids.salesmanagement.model.Client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientReportDTO {
    private int totalClients;
    private Map<Long, Double> topSpendingClients;
//    private Map<Client, Long> ClientActivity;
    private Map<String, Long> ClientsLocationStatistics;
}
