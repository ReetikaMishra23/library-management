
package com.kiet.library.service;

import com.kiet.library.model.Book;
import com.kiet.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(int bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(int bookId, Book updatedBook) {
        Book existingBook = getBookById(bookId);

        if (existingBook == null) {
            return null;
        }

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setPublicationYear(
                updatedBook.getPublicationYear());
        existingBook.setAvailable(updatedBook.isAvailable());

        return bookRepository.save(existingBook);
    }

    public boolean deleteBook(int bookId) {
        Book book = getBookById(bookId);

        if (book == null) {
            return false;
        }

        bookRepository.delete(book);
        return true;
    }

    public List<Book> searchByCategory(String category) {
        return bookRepository.findAll()
                .stream()
                .filter(book ->
                        book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    public List<Book> findPublishedFromYear(int fromYear) {
        return bookRepository.findAll()
                .stream()
                .filter(book ->
                        book.getPublicationYear() >= fromYear)
                .toList();
    }
}
