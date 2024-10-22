package com.example.myapp.service;

import com.example.myapp.entity.Book;
import com.example.myapp.entity.Cart;
import com.example.myapp.entity.Cart_Item;
import com.example.myapp.entity.User;
import com.example.myapp.repository.CartRepository;
import com.example.myapp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class CartService {
    private final BookService bookService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Autowired
    private HttpSession httpSession;

    public CartService(CartRepository cartRepository, BookService bookService, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.bookService = bookService;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addBookToCart(String username, Long bookId){
        if (username != null) {
            // Логика для зарегистрированных пользователей
            User user = userRepository.findByUsername(username);
            Cart cart = cartRepository.findByUser(user);
            if(cart == null){
                cart = new Cart();
                cart.setUser(user);
            }

            Book book = bookService.findById(bookId);
            Cart_Item existingItem = cart.getItems().stream().filter(
                    item -> item.getBook().getId().equals(book.getId())).findFirst().orElse(null);

            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity()+1);
            } else {
                Cart_Item newItem = new Cart_Item(book, 1, cart);
                cart.getItems().add(newItem);
            }
            cartRepository.save(cart);
        } else {
            // Логика для гостей
            List<Cart_Item> guestCart = (List<Cart_Item>) httpSession.getAttribute("guestCart");
            if (guestCart == null) {
                guestCart = new ArrayList<>();
            }

            Book book = bookService.findById(bookId);
            Cart_Item existingItem = guestCart.stream().filter(
                    item -> item.getBook().getId().equals(book.getId())).findFirst().orElse(null);

            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + 1);
            } else {
                Cart_Item newItem = new Cart_Item(book, 1, null); // Указываем null, так как это для гостевой корзины
                guestCart.add(newItem);
            }
            httpSession.setAttribute("guestCart", guestCart);
        }
    }

    @Transactional
    public void removeBookFromCart(String username, Long bookId){
        if (username != null) {
            // Логика для зарегистрированных пользователей
            User user = userRepository.findByUsername(username);
            Cart cart = cartRepository.findByUser(user);
            if(cart != null){
                cart.getItems().removeIf(item -> item.getBook().getId().equals(bookId));
            }
            cartRepository.save(cart);
        } else {
            // Логика для гостей
            List<Cart_Item> guestCart = (List<Cart_Item>) httpSession.getAttribute("guestCart");
            if (guestCart != null) {
                guestCart.removeIf(item -> item.getBook().getId().equals(bookId));
                httpSession.setAttribute("guestCart", guestCart);
            }
        }
    }

    @Transactional
    public void clearCart(String username){
        if (username != null) {
            User user = userRepository.findByUsername(username);
            Cart cart = cartRepository.findByUser(user);
            if(cart != null){
                cart.getItems().clear();
                cartRepository.save(cart);
            }
        } else {
            // Очистка гостевой корзины
            httpSession.removeAttribute("guestCart");
        }
    }

    public List<Cart_Item> getCartItems(String username){
        if (username != null) {
            User user = userRepository.findByUsername(username);
            Cart cart = cartRepository.findByUser(user);
            if(cart != null) {
                return cart.getItems();
            } else {
                return new ArrayList<>();
            }
        } else {
            // Возвращаем гостевую корзину
            List<Cart_Item> guestCart = (List<Cart_Item>) httpSession.getAttribute("guestCart");
            return guestCart != null ? guestCart : new ArrayList<>();
        }

    }

}
