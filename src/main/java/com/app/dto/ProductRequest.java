package com.app.dto;

import org.hibernate.validator.constraints.NotBlank;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {

	    @NotBlank(message = "Name is required")
	    private String productName;

	    @NotBlank(message = "Description is required")
	    private String productDescription;

	    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
	    private Double productPrice;

	    @NotNull(message = "Category ID is required")
	    private Long categoryId;
}
