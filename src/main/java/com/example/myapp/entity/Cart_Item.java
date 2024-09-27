package com.example.myapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cart_Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    @ManyToOne
    private Book book;

    @ManyToOne
    private Cart cart;

    public Cart_Item() {
    }
    public Cart_Item(Book book, int quantity, Cart cart) {
        this.book = book;
        this.quantity = quantity;
        this.cart = cart;
    }


}
