package com.example.myapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long size;

    private String name;

    private String originalFileName;

    private String contentType;

    private boolean isPreviewImage;

    private long previewImageId;

    @Lob
    @Column(name = "bytes", columnDefinition = "longblob")
    private byte[] bytes;
    @ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private Book book;
}
