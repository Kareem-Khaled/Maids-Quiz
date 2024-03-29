package com.maids.salesmanagement.repository;

import com.maids.salesmanagement.model.Sale;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByCreationDateBetween(Date startDate, Date endDate);

}
