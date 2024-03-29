package com.maids.salesmanagement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String lastName;
    private String mobile;
    private String email;
    private String password;
    private String address;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="client_roles", joinColumns=@JoinColumn(name="client_id"))
    private List<String> roles = new ArrayList<>();
    
    public Client(String email, String password) {
    	this.email = email;
    	this.password = password;
    }
}
