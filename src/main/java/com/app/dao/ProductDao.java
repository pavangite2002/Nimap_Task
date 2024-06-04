package com.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.dto.ProductRequest;
import com.app.model.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Long>{
	
	Page<Product> findAll(Pageable pageable);
	Product findByProductId(Long id);
}
