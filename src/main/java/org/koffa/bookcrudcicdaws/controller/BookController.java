package org.koffa.bookcrudcicdaws.controller;

import lombok.RequiredArgsConstructor;
import org.koffa.bookcrudcicdaws.dao.BookDao;
import org.koffa.bookcrudcicdaws.entity.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookDao bookDao;

    @GetMapping("")
    public ResponseEntity<List<Book>> getBooks() {
        return bookDao.scan().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bookDao.scan());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable String id) {
        try {
            return bookDao.getBook(id) == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(bookDao.getBook(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        try {
            if(book.getId() != null)
                return ResponseEntity.badRequest().build();
            Book newBook = bookDao.save(book);
            return ResponseEntity.ok(newBook);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        try {
            bookDao.update(book);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("")
    public ResponseEntity<String> deleteBook(@RequestBody Book book) {
        try {
            bookDao.delete(book);
            return ResponseEntity.ok("Book deleted");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
