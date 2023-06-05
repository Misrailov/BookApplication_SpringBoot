package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import domain.Auteur;
import domain.Book;
import exception.BookNotFoundException;
import exception.DuplicateBookException;
import repository.BookRepository;

public class BookServiceImpl implements BookService{
	
	
	@Autowired
	private BookRepository bookRepo;

	@Override
	public Book getBook(Long id) {
		Optional<Book> book = bookRepo.findById(id);
		if(book.get() ==null) {
			 throw new BookNotFoundException(book.get().getId());
		}
		return book.get();
	}

	@Override
	public List<Book> getAllBooks() {
		Iterable<Book> source = bookRepo.findAll();
		List<Book> books = new ArrayList<>();
		source.forEach(books::add);
		return books;
	}

	@Override
	public Book createBook(Book book) {
		System.out.println();
		if(!bookRepo.findByISBNNumber(book.getISBNNumber()).isEmpty()) {
			 throw new DuplicateBookException(book.getISBNNumber());
		}
		bookRepo.save(book);
		return book;
	}

	@Override
	public Book deleteBook(Long id) {
		Optional<Book> book = bookRepo.findById(id);
		if(book.get() ==null) {
			 throw new BookNotFoundException(book.get().getId());
		}
		bookRepo.delete(book.get());
		return book.get();
	}

	@Override
	public Book updateBook(Book book) {
		Optional<Book> bookToUpdate = bookRepo.findById(book.getId());
		if(bookToUpdate.get() ==null) {
			 throw new BookNotFoundException(bookToUpdate.get().getId());
		}
		bookToUpdate.get().setAankoopprijs(book.getAankoopprijs());
		bookToUpdate.get().setAantalSterren(book.getAantalSterren());
		bookToUpdate.get().setNaam(book.getNaam());
		bookToUpdate.get().setLocaties(book.getLocaties());
		bookToUpdate.get().setAuteur(book.getAuteurs());
		bookRepo.save(bookToUpdate.get());
		

		
		return bookToUpdate.get();
	}

	@Override
	public List<Book> getBooksByAuteur(String name, String firstName) {
		System.out.println("NAMES"+ name+ "  " + firstName);
		Iterable<Book> source = bookRepo.findAll();
		List<Book> booksByAuthor = new ArrayList<>();

		for(Book book: source) {
			if(book.getAuteurs().stream()
					.filter(x ->x.getFirstname().trim()
							.toLowerCase().contains(firstName.trim().toLowerCase())&&x.getNaam().trim().toLowerCase().contains(name.trim().toLowerCase())).toList().size()>0) {
				System.out.println(book);
				booksByAuthor.add(book);
			}
		}
		
		return booksByAuthor;
	}

	@Override
	public Book getByISBNNumber(String ISBNNumber) {
		Optional<Book> book = bookRepo.findByISBNNumber(ISBNNumber);
		if(book.isEmpty()) {
			 throw new BookNotFoundException(ISBNNumber);
		}
		
		return book.get();
	}
	@Override
	public Optional<Book> findByISBNOptional(String ISBNNumber) {
		Optional<Book> book = bookRepo.findByISBNNumber(ISBNNumber);
		
		
		return book;
	}

	@Override
	public List<Book> findByNaam(String naam) {
		// TODO Auto-generated method stub
		return bookRepo.findByNaam(naam);
	}


	
	

}
