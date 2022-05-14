package com.juan.bookbroker.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.juan.bookbroker.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
	List<Book> findAll();
}
