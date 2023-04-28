package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import domain.Admin;
import domain.Book;

public interface BookRepository extends CrudRepository<Book, String> {
	
	List<Book> findByNaam(String naam);
	List<Book> findByAuteurs(String auteurs);
//	List<Book> findByISBNNumber(String ISBNNumber);
	
}
