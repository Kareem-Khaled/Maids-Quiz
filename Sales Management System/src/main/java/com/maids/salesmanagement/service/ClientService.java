package com.maids.salesmanagement.service;

import com.maids.salesmanagement.exception.ResourceNotFoundException;
import com.maids.salesmanagement.model.Client;
import com.maids.salesmanagement.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientId));
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long clientId, Client clientDetails) {
        Client client = getClientById(clientId);
        client.setName(clientDetails.getName());
        client.setLastName(clientDetails.getLastName());
        client.setMobile(clientDetails.getMobile());
        client.setEmail(clientDetails.getEmail());
        client.setAddress(clientDetails.getAddress());
        return clientRepository.save(client);
    }

    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }
}
