package repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import domain.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
	
	List<Book> findByNaam(String naam);
	List<Book> findByAuteurs(String auteurs);
//	List<Book> findByISBNNumber(String ISBNNumber);
	Optional<Book> findByISBNNumber(String ISBNNumber);
	
}
