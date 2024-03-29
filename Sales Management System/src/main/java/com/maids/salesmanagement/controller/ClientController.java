package com.maids.salesmanagement.controller;

import com.maids.salesmanagement.dto.ClientDTO;
import com.maids.salesmanagement.exception.ResourceNotFoundException;
import com.maids.salesmanagement.model.Client;
import com.maids.salesmanagement.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
@Validated // Enables method-level validation
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return clients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        return convertToDTO(client);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        Client client = convertToEntity(clientDTO);
        Client createdClient = clientService.createClient(client);
        return new ResponseEntity<>(convertToDTO(createdClient), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ClientDTO updateClient(@PathVariable Long id, @Valid @RequestBody ClientDTO clientDTO) {
        Client client = convertToEntity(clientDTO);
        Client updatedClient = clientService.updateClient(id, client);
        return convertToDTO(updatedClient);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    // Utility method to convert Client entity to ClientDTO
    private ClientDTO convertToDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }

    // Utility method to convert ClientDTO to Client entity
    private Client convertToEntity(ClientDTO clientDTO) {
        return modelMapper.map(clientDTO, Client.class);
    }
}
