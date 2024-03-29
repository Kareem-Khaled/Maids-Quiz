package com.maids.salesmanagement.model;

import lombok.Data;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;
    
    private int availableQuantity;
    private double price;
    
    @CreationTimestamp
    private Date creationDate;
}
