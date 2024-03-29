package com.maids.salesmanagement.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.maids.salesmanagement.dto.ProductDTO;
import com.maids.salesmanagement.model.Client;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Client.class, ProductDTO.class);

        return modelMapper;
    }
}
