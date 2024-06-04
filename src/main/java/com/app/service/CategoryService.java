package com.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.model.Category;
import com.app.model.Product;

import jakarta.validation.Valid;

public interface CategoryService {

	Category save(Category category);
	Category update(Long id,Category request);
	Category getById(Long id);
	void delete(Long id);
	Page<Category> getAll(int page, int size);
	
}
