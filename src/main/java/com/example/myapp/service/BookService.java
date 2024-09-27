package com.example.myapp.service;

import com.example.myapp.entity.Book;
import com.example.myapp.entity.Image;
import com.example.myapp.exception.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.myapp.repository.BookRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<Book> findByTitleOrAuthor(String title, String author) {
        // Реализация поиска по названию или автору
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(title, author);
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
    public Book addBook(Book book,MultipartFile file1,MultipartFile file2,MultipartFile file3) throws IOException {
        Image image1;
        Image image2;
        Image image3;
        if(file1.getSize()!= 0){
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            book.addImageToBook(image1);
        }
        if(file2.getSize()!= 0){
            image2 = toImageEntity(file2);
            book.addImageToBook(image2);
        }
        if(file3.getSize()!= 0){
            image3 = toImageEntity(file3);
            book.addImageToBook(image3);
        }
        log.info("Saving new Product. Title: {}; Author: {}", book.getTitle(), book.getAuthor());
        Book bookFromDb = bookRepository.save(book);
        bookFromDb.setPreviewImageId(bookFromDb.getImages().get(0).getId());
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Object getBookById(Long id){
        bookRepository.findById(id);
        return null;
    }

    public Book findById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId + " not found"));
    }

}
