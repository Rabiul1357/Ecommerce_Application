package com.ecommerce.service;

import com.ecommerce.repository.ProductRepository;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.CartItemRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class CartService {
	
	private final CartItemRepository cartRepository;
	private final ProductRepository productRepository;

	public CartService(CartItemRepository cartRepository, ProductRepository productRepository) {
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
	}

	public void addToCart(Long productId) {

		Product product = productRepository.findById(productId).get();

		if (product.getStock() <= 0) {
			throw new RuntimeException("Product Out Of Stock");
		}

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		CartItem item = cartRepository.findByProductAndUsername(product, username).orElse(null);

		if (item != null) {

			item.setQuantity(item.getQuantity() + 1);
			cartRepository.save(item);

		} else {

			CartItem newItem = new CartItem();
			newItem.setProduct(product);
			newItem.setQuantity(1);

			newItem.setUsername(username);

			cartRepository.save(newItem);
		}

		// Decrease stock
		product.setStock(product.getStock() - 1);
		productRepository.save(product);
	}

	public List<CartItem> getCartItems() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		return cartRepository.findByUsername(username);
	}

	public void removeItem(Long id) {

		CartItem item = cartRepository.findById(id).get();

		Product product = item.getProduct();

		product.setStock(product.getStock() + item.getQuantity());

		productRepository.save(product);

		cartRepository.deleteById(id);
	}

	public double getTotalPrice() {

	    String username = SecurityContextHolder
	            .getContext()
	            .getAuthentication()
	            .getName();

	    double total = 0;

	    for (CartItem item : cartRepository.findByUsername(username)) {

	        total += item.getProduct().getPrice() * item.getQuantity();
	    }

	    return total;
	}

	@Transactional
	public void clearCart() {

	    String username = SecurityContextHolder
	            .getContext()
	            .getAuthentication()
	            .getName();

	    List<CartItem> items = cartRepository.findByUsername(username);

	    cartRepository.deleteAll(items);
	}
}