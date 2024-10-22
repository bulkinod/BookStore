package com.example.myapp.controller;

import com.example.myapp.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.myapp.service.BookService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/search")
    public List<Book> bookSearch(@RequestParam(value = "author",required = false) String author,
                                 @RequestParam(value = "title",required = false) String title){
        if (author != null && title != null) {
            return bookService.findByTitleOrAuthor(title, author);
        } else if (title != null) {
            return bookService.searchBooksByTitle(title);
        } else if (author != null) {
            return bookService.searchBooksByAuthor(author);
        } else {
            return bookService.findAllBooks();
        }
    }


    @GetMapping("/{id}")
    public String bookDescription(@PathVariable Long id, Model model){
        model.addAttribute("book", bookService.getBookById(id));
        return "book description";
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) throws IOException {
        return bookService.addBook(book);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
