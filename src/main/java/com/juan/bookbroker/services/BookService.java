package com.juan.bookbroker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juan.bookbroker.models.Book;
import com.juan.bookbroker.repositories.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepo;
	
//	READ ALL
	public List<Book> allBooks(){
		return bookRepo.findAll();
	}
	
//	CREATE
	public Book createdBook(Book book) {
		return bookRepo.save(book);
	}
	
//	READ ONE
	public Book findBook(Long id) {
		Optional <Book> optionalBook = bookRepo.findById(id);
		if(optionalBook.isPresent()) {
			return optionalBook.get();
		} else {
			return null;
		}
	}
	
//	UPDATE
	public Book updateBook(Book book) {
		return bookRepo.save(book);
	}
	
//	DELETE
	public void deleteBook (Long id) {
		bookRepo.deleteById(id);
	}
}
