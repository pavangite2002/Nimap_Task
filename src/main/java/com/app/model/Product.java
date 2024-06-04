package com.app.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@XmlRootElement(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	@NotBlank(message = "Name is required")
	private String productName;
	
	@NotBlank(message = "Description is required")
	private String productDescription;
	
	@DecimalMin(value = "0.01", message = "Price must be greater than 0")
	@Column(columnDefinition = "double(10,2)")
    private Double productPrice;
	
	
	@CreationTimestamp 
	@Column(name = "CREATED_DATE", updatable = false) 
	private LocalDateTime createdDate; 
	
	@UpdateTimestamp 
	@Column(name = "UPDATED_DATE", insertable = false) 
	private LocalDateTime updatedDate;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;
		
}
