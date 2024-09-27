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

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "book")
    private List<Image> images = new ArrayList<>();

    private long previewImageId;

    private LocalDateTime creationDate;

    @PrePersist
    private void init(){
        creationDate = LocalDateTime.now();
    }

    public void addImageToBook(Image image) {
        image.setBook(this);
        images.add(image);
    }
}
