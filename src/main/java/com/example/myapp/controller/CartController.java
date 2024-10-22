package com.example.myapp.controller;

import com.example.myapp.entity.Cart_Item;
import com.example.myapp.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/view")
    public ResponseEntity<List<Cart_Item>> viewCart(@RequestParam("username")String username){
        List<Cart_Item> items = cartService.getCartItems(username);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam("username") String username, @RequestParam("bookId") Long bookId){
        cartService.addBookToCart(username,bookId);
        return ResponseEntity.ok("book was added to cart");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestParam("username") String username, @RequestParam("bookId") Long bookId){
        cartService.removeBookFromCart(username, bookId);
        return ResponseEntity.ok("book was removed from cart");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> clearCart(@RequestParam("username") String username){
        cartService.clearCart(username);
        return ResponseEntity.ok("cart was cleared");
    }
}
