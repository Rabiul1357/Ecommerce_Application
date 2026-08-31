package com.ecommerce.controller;

import com.ecommerce.entity.AppUser;
import com.ecommerce.entity.Product;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class PageController {

	private final ProductService service;
	private final OrderService orderService;

	@Autowired
	private UserService userService;

	public PageController(ProductService service, OrderService orderService) {
		this.service = service;
		this.orderService = orderService;
	}

	@GetMapping("/")
	public String home(Model model) {

		model.addAttribute("products", service.getAll());

		return "products";
	}

	@GetMapping("/search")
	public String searchProducts(@RequestParam String keyword, Model model) {

		model.addAttribute("products", service.searchProducts(keyword));

		return "products";
	}

	@GetMapping("/orders")
	public String orders(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String username = auth.getName();

		model.addAttribute("orders", orderService.getOrdersByUsername(username));

		return "orders";
	}

	@GetMapping("/add-product")
	public String addProductPage(Model model) {

		model.addAttribute("product", new Product());

		return "add-product";
	}

	@PostMapping("/save-product")
	public String saveProduct(@ModelAttribute Product product) {

		service.save(product);

		return "redirect:/admin";
	}

	// Admin
	@GetMapping("/admin")
	public String adminDashboard(Model model) {

		model.addAttribute("totalProducts", service.getAll().size());

		model.addAttribute("totalOrders", orderService.getOrders().size());

		return "admin";
	}

	@GetMapping("/manage-products")
	public String manageProducts(Model model) {

		model.addAttribute("products", service.getAll());

		return "manage-products";
	}

	@GetMapping("/edit-product/{id}")
	public String editProduct(@PathVariable Long id, Model model) {

		model.addAttribute("product", service.getById(id));

		return "edit-product";
	}

	@PostMapping("/update-product")
	public String updateProduct(@ModelAttribute Product product) {

		service.update(product.getId(), product);

		return "redirect:/manage-products";
	}

	@GetMapping("/delete-product/{id}")
	public String deleteProduct(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/manage-products";
	}

	@GetMapping("/register")
	public String registerPage(Model model) {

		model.addAttribute("user", new AppUser());

		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute AppUser user) {

		userService.save(user);

		return "redirect:/login";
	}

	@GetMapping("/admin/orders")
	public String adminOrders(Model model) {

		model.addAttribute("orders", orderService.getOrders());

		return "admin-orders";
	}
	
	
	@GetMapping("/login")
	public String loginPage() {
	    return "login";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied() {
	    return "access-denied";
	}	

}