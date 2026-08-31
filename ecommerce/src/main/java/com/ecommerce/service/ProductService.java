package com.ecommerce.service;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

	private final ProductRepository repository;

	public ProductService(ProductRepository repository) {
		this.repository = repository;
	}

	public Product save(Product product) {
		return repository.save(product);
	}

	public List<Product> getAll() {
		return repository.findAll();
	}

	//To upadate product
	public Product update(Long id, Product product) {

	    Product existing = repository.findById(id).orElse(null);

	    if (existing != null) {

	        existing.setName(product.getName());
	        existing.setPrice(product.getPrice());
	        existing.setStock(product.getStock());

	        existing.setImageUrl(product.getImageUrl());

	        return repository.save(existing);
	    }

	    return null;
	}
	
	
	//To delete product
	public void delete(Long id) {
	    repository.deleteById(id);
	}
	
	//To search
	public List<Product> searchProducts(String keyword) {
	    return repository.findByNameContainingIgnoreCase(keyword);
	}
	
	
	
	
	public Product getById(Long id) {
	    return repository.findById(id).orElse(null);
	}

//	public Product update(Long id, Product product) {
//
//	    Product existing = repository.findById(id).orElse(null);
//
//	    if (existing != null) {
//
//	        existing.setName(product.getName());
//	        existing.setPrice(product.getPrice());
//	        existing.setStock(product.getStock());
//
//	        return repository.save(existing);
//	    }
//
//	    return null;
//	}
//
//	public void delete(Long id) {
//	    repository.deleteById(id);
//	}
	
}