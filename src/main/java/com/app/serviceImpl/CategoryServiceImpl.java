package com.app.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.dao.CategoryDao;
import com.app.exception.EntityNotFoundException;
import com.app.model.Category;
import com.app.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Override
	public Category save(Category category) {
		return categoryDao.save(category);
	}

	public Category update(Long id, Category request) {
		Category category = categoryDao.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

		category.setCategoryName(request.getCategoryName());
		category.setCategoryDescription(request.getCategoryDescription());

		return categoryDao.save(category);
	}

	public Category getById(Long id) {
		return categoryDao.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
	}

	public void delete(Long id) {
		Category category = categoryDao.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
		categoryDao.delete(category);
	}

	public Page<Category> getAll(int page, int size) {
		return categoryDao.findAll(PageRequest.of(page, size));
	}
}
