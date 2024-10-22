package com.example.myapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private double price;

    private String description;

    private LocalDateTime creationDate;

    @PrePersist
    private void init(){
        creationDate = LocalDateTime.now();
    }


}
