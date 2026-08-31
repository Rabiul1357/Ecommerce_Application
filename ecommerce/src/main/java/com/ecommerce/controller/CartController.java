package com.ecommerce.controller;

import com.ecommerce.service.CartService;
import com.ecommerce.service.OrderService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

	private final CartService service;
	private final OrderService orderService;

	public CartController(CartService service, OrderService orderService) {

		this.service = service;
		this.orderService = orderService;
	}

	@GetMapping("/cart")
	public String viewCart(Model model) {

		model.addAttribute("cartItems", service.getCartItems());

		model.addAttribute("total", service.getTotalPrice());

		return "cart";
	}

	@PostMapping("/cart/add/{id}")
	public String addToCart(@PathVariable Long id) {

		service.addToCart(id);

		return "redirect:/";
	}

	@GetMapping("/cart/remove/{id}")
	public String removeItem(@PathVariable Long id) {

		service.removeItem(id);

		return "redirect:/cart";
	}
	
	@PostMapping("/checkout")
	public String checkout() {

	    try {

	        double total = service.getTotalPrice();

	        orderService.placeOrder(total);

	        service.clearCart();

	        return "success";

	    } catch (Exception e) {

	        e.printStackTrace();

	        return "redirect:/cart";
	    }
	}

}