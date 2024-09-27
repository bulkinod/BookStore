package com.example.myapp.repository;

import com.example.myapp.entity.Cart;
import com.example.myapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUser(User user);
}
