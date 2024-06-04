package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.app.dto.ProductRequest;
import com.app.model.Category;
import com.app.serviceImpl.CategoryServiceImpl;
import com.app.serviceImpl.ProductServiceImpl;

@SpringBootApplication
public class ProductCategoryApiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ProductCategoryApiApplication.class, args);
		ProductServiceImpl bean = run.getBean(ProductServiceImpl.class);
		
		
	}

}
