package com.app.dto;

import lombok.Data;

@Data
public class ProductResponse {

	 	private Long productId;
	    private String productName;
	    private String productDescription;
	    private Double productPrice;
	    private Long categoryId;
	    private String categoryName;
}
