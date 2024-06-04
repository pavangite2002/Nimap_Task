package com.app.serviceImpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.CategoryDao;
import com.app.dao.ProductDao;
import com.app.dto.ProductRequest;
import com.app.dto.ProductResponse;
import com.app.model.Category;
import com.app.model.Product;
import com.app.service.ProductService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private CategoryDao categoryDao;

	@Transactional
	public Product save(ProductRequest request) {
		Category category = categoryDao.findByCategoryId(request.getCategoryId());
		if (category != null) {
			Product p = new Product();
			BeanUtils.copyProperties(request, p, "categoryId");
			p.setCategory(category);
			p = productDao.save(p);

			category.getProducts().add(p);
			categoryDao.save(category);

			return p;
		} else {
			throw new EntityNotFoundException("Category not found with id: " + request.getCategoryId());
		}
	}
	
	@Transactional
	public Product update(Long id, ProductRequest request) {
       Product product = productDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        product.setProductName(request.getProductName());
        product.setProductDescription(request.getProductDescription());
        product.setProductPrice(request.getProductPrice());

       return productDao.save(product);
    }

	@Override
	public Page<Product> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ProductResponse getProductWithCategoryDetails(Long id) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        Category category = categoryDao.findById(product.getCategory().getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found for product with id: " + id));

        ProductResponse response = new ProductResponse();
        response.setProductId(product.getProductId());
        response.setProductName(product.getProductName());
        response.setProductDescription(product.getProductDescription());
        response.setProductPrice(product.getProductPrice());
        response.setCategoryId(category.getCategoryId());
        response.setCategoryName(category.getCategoryName());

        return response;
    }
	
	public void deleteProduct(Long id) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        productDao.delete(product);
    }
	
	
	 public Page<Product> getAllProducts(int page, int size) {
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Product> products = productDao.findAll(pageable);
	        System.out.println(products);
	        return products;
	    }
}
