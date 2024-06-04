package com.app.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.EntityNotFoundException;
import com.app.model.Category;
import com.app.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// hander for creating category
	@PostMapping(
			consumes = { "application/json", "application/xml" }
			)
	public ResponseEntity<String> createCategory(@RequestBody @Valid Category request, BindingResult result) {
		if (result.hasErrors()) {
			String errorMessage = result.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage())
					.collect(Collectors.joining("\n"));
			return ResponseEntity.badRequest().body(errorMessage);
		}

		Category createdCategory = categoryService.save(request);
		return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully");
	}

	// handler for updating category by using id
	@PutMapping(value = "/{id}",
			consumes = { "application/json", "application/xml" }
		)
	public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody @Valid Category request,
			BindingResult result) {
		if (result.hasErrors()) {
			String errorMessage = result.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage())
					.findFirst().orElse("Validation error");
			return ResponseEntity.badRequest().body(errorMessage);
		}

		try {
			Category updatedCategory = categoryService.update(id, request);
			return ResponseEntity.ok("Category updated successfully");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// handler for retriving category by id
	@GetMapping(value = "/{id}",
			produces ={ "application/json", "application/xml" }
			)
	public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
		try {
			Category category = categoryService.getById(id);
			return ResponseEntity.ok(category);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// handler for deleting category
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
		try {
			categoryService.delete(id);
			return ResponseEntity.ok("Category deleted successfully");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// handler for retriving multiple data with pagination
	@GetMapping
	public ResponseEntity<Page<Category>> getAllCategories(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Page<Category> categoriesPage = categoryService.getAll(page, size);
		return ResponseEntity.ok(categoriesPage);
	}
}
