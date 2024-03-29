package com.maids.salesmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.maids.salesmanagement.model.Product;
import com.maids.salesmanagement.model.Client;
import com.maids.salesmanagement.model.ProductCategory;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer{

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Client.class);
        config.exposeIdsFor(Product.class);
        config.exposeIdsFor(ProductCategory.class);
	}
	
}
