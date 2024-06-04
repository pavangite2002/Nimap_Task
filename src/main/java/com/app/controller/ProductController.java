package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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

import com.app.dto.ProductRequest;
import com.app.dto.ProductResponse;
import com.app.exception.EntityNotFoundException;
import com.app.model.Category;
import com.app.model.Product;
import com.app.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	
	//handler which handle post request for creating product
	@PostMapping(
			consumes = { "application/json", "application/xml" }
			)
	public ResponseEntity<String> createProduct(@RequestBody @Valid ProductRequest request, BindingResult result) {
		if (result.hasErrors()) {
			String errorMessage = result.getFieldErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().orElse("Validation error");
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}

		try {
			Product product = productService.save(request);
			return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//handler to handle the put request for updationg product
	@PutMapping(value = "/{id}",
				consumes = { "application/json", "application/xml" }
			)
	public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest request, BindingResult result) {
	    if (result.hasErrors()) {
	        
	    	String errorMessage =result.getFieldErrors().stream()
	                .map(DefaultMessageSourceResolvable::getDefaultMessage)
	                .findFirst()
	                .orElse("Validation error");
	        return ResponseEntity.badRequest().body(errorMessage);

	    }

	    try {
	        Product updatedProduct = productService.update(id, request);
	        return ResponseEntity.ok("Product updated successfully");
	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	//handler for retrieving single product with category details
	@GetMapping(value = "/{id}",
			produces ={ "application/json", "application/xml" }
			)
    public ResponseEntity<ProductResponse> getProductDetails(@PathVariable Long id) {
        try {
            ProductResponse productResponse = productService.getProductWithCategoryDetails(id);
            return ResponseEntity.ok(productResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
	
	//handler for deleting product
	@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
	
	//handler for retriving all product by pagination
	@GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(defaultValue = "0",required = false) int page,
                                                        @RequestParam(defaultValue = "10",required = false) int size) {
        Page<Product> productsPage = productService.getAllProducts(page, size);
        return ResponseEntity.ok(productsPage);
    }
	
}
