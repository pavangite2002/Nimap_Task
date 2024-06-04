package com.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.dto.ProductRequest;
import com.app.dto.ProductResponse;
import com.app.model.Product;

public interface ProductService {
	
	Page<Product> findAll(Pageable pageable);
	Product save(ProductRequest request);
	Product update(Long id, ProductRequest request);
	ProductResponse getProductWithCategoryDetails(Long id);
	void deleteProduct(Long id);
	Page<Product> getAllProducts(int page, int size);
}
