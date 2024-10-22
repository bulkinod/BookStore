package com.example.myapp.service;

import com.example.myapp.entity.Book;
import com.example.myapp.exception.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.myapp.repository.BookRepository;
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



    public Book addBook(Book book) throws IOException {


        log.info("Saving new Product. Title: {}; Author: {}", book.getTitle(), book.getAuthor());
        Book bookFromDb = bookRepository.save(book);
        return bookFromDb;
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
