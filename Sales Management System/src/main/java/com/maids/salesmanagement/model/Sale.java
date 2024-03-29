package com.maids.salesmanagement.model;

import lombok.Data;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItem> items;

    private Long clientId;

    private Long sellerId;

    private double total;
    
    @CreationTimestamp
    private Date creationDate;
    
    public List<SaleItem> getI(){
    	return this.items;
    }
}
