package com.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Category;
import com.app.model.Product;

@Repository
public interface CategoryDao extends JpaRepository<Category, Long>{
	
	Page<Category> findAll(Pageable pageable);
	Long deleteByCategoryId(Long id);
	Category findByCategoryId(Long id);
}
