package com.ecommerce.service;

import com.ecommerce.entity.Order;
import com.ecommerce.repository.OrderRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

	private final OrderRepository repository;

	public OrderService(OrderRepository repository) {
		this.repository = repository;
	}

	public Order placeOrder(double total) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		Order order = new Order();

		order.setTotalAmount(total);
		order.setOrderDate(LocalDateTime.now());
		order.setUsername(username);

		return repository.save(order);
	}

	public List<Order> getOrders() {
		return repository.findAll();
	}

	public List<Order> getOrdersByUsername(String username) {
		return repository.findByUsername(username);
	}
}