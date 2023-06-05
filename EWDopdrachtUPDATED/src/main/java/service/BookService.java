package service;

import java.util.List;
import java.util.Optional;

import domain.Auteur;
import domain.Book;

public interface BookService {

	List<Book> findByNaam(String naam);
	public Book getBook(Long id);
	public List<Book> getAllBooks();
	public Book createBook(Book book);
	public Book deleteBook(Long id);
	public Book updateBook(Book book);
	public List<Book> getBooksByAuteur(String name, String firstName);
	public Book getByISBNNumber(String ISBNNumber);
	Optional<Book> findByISBNOptional(String ISBNNumber);
}
