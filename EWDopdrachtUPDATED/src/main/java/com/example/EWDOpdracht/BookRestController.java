package com.example.EWDOpdracht;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import domain.Book;
import domain.Favourite;
import service.BookService;
import service.FavouriteService;

@RestController
@RequestMapping(value = "/rest")
public class BookRestController {
	@Autowired
	BookService bookService;
	@Autowired
	FavouriteService favService;
	
	@GetMapping(value = "/book/{bookId}")
	public Book getBook(@PathVariable Long bookId) {
		return bookService.getBook(bookId);
	}
	
	@GetMapping(value = "/books")
	public List<Book> getAllBooks(){
		return bookService.getAllBooks();
	}
	
	@PostMapping(value = "/book/create")
	public Book createBook (@RequestBody Book book) {
		return bookService.createBook(book);
	}
	@DeleteMapping(value = "/book/delete/{bookId}")	
	public Book deleteBook (@PathVariable long bookId) {
		return bookService.deleteBook(bookId);
	}
	@PutMapping(value = "/book/{bookId}")
	public Book updateBook(@PathVariable Long bookId,@RequestBody Book book) {
		return bookService.updateBook(book);
	}
	@GetMapping(value = "/favourite")
	public List<Favourite> getAllFavourites(){
		return favService.getAllFavourites();
		
	}
	@GetMapping(value = "/author/{name}")
	public List<Book> getBooksByAuthor(@PathVariable String name){
		String firstName = name.split("_")[0];
		String lastName = name.split("_")[1];

		return bookService.getBooksByAuteur(lastName, firstName);
		
	}
	@GetMapping(value = "/ISBN/{ISBNNumber}")
	public Book getBooksISBNNumber(@PathVariable String ISBNNumber){
		

		return bookService.getByISBNNumber(ISBNNumber);
		
	}
	
	
}

















