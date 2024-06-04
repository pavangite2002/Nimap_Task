package com.app.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@XmlRootElement(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long categoryId;
	
	@NotBlank(message = "Name is required")
	private String categoryName;
	
	@NotBlank(message = "Description is required")
	private String categoryDescription;
	
	@CreationTimestamp 
	@Column(name = "CREATED_DATE", updatable = false) 
	private LocalDateTime createdDate; 
	
	@UpdateTimestamp 
	@Column(name = "UPDATED_DATE", insertable = false) 
	private LocalDateTime updatedDate;

	@OneToMany(mappedBy ="category", cascade = CascadeType.ALL)
	private List<Product> products;
	
	
	@Override
	public String toString() {
	    return "Category{" +
	            "categoryId=" + categoryId +
	            ", categoryName='" + categoryName + '\'' +
	            '}';
	}

}
