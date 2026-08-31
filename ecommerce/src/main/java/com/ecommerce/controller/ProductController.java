package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService service;

	public ProductController(ProductService service) {
		this.service = service;
	}

	@PostMapping
	public Product addProduct(@RequestBody Product product) {

		return service.save(product);
	}

	@GetMapping
	public List<Product> getAllProducts() {
		return service.getAll();
	}

	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {

		return service.update(id, product);
	}
	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable Long id) {

	    service.delete(id);

	    return "Product Deleted Successfully";
	}
}