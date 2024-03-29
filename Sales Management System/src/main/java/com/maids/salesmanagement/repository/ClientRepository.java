package com.maids.salesmanagement.repository;

import com.maids.salesmanagement.model.Client;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	Client findByEmail(@RequestParam("email") String email);
	
    @Query("SELECT c.address, COUNT(c) FROM Client c GROUP BY c.address")
    List<Object[]> countClientsByAddress();
}
