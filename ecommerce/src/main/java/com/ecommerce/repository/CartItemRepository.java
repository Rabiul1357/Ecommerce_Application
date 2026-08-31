package com.ecommerce.repository;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByProductAndUsername(Product product, String username);

    List<CartItem> findByUsername(String username);
    
    
}