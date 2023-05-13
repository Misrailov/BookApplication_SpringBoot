package com.example.EWDOpdracht;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import validator.BookValidation;
import domain.Auteur;
import domain.Book;
import domain.BookForm;
import jakarta.validation.Valid;
import repository.BookRepository;
import utility.Message;

@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private BookRepository repository;

	@GetMapping
	public String listBook(Model model) {
		
		model.addAttribute("bookList", repository.findAll());
		model.addAttribute("bookName", repository.findByNaam("The Lord of the Rings"));
		//model.addAttribute("bookAuthor", repository.findByAuteurs("J.R.R. Tolkien"));
		return "book";
	}
	
	@GetMapping(value = "/new")
	public String createForm( Model model) {
		BookForm bookForm = new BookForm();

		model.addAttribute("bookForm",bookForm);
//		model.addAttribute("book", bookForm.getBook())
		 

		return "/new";
		
	}
	
	@GetMapping(value ="/{id}")
	public String showBook(@PathVariable("id") Long id, Model model) {
		Optional<Book> book = repository.findById(id);
		System.out.println(book);
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||____________________________________________");
		if(book == null) {
			return "redirect:/book";			
		}

		System.out.println(book.get().getAuteurs());
		model.addAttribute("book",book.get());
		return "/bookShow";
	}
	@PostMapping(value = "/new")
	public String create(@Valid BookForm bookform, BindingResult result, Model model, Locale locale) {
		
	    if (result.hasErrors()) {
	        model.addAttribute("message", new Message("error", messageSource.getMessage("book_save_fail", new Object[]{}, locale)));
	        return "/new";
	    }
	    System.out.println(bookform.getBook());
	    
	    System.out.println(bookform.getAuteurs());
	    Set<Auteur> auteursToAdd = new HashSet<>();
	    bookform.getAuteurs().forEach(x -> {
	    	if(x.getFirstname().contains("First Name")) {
	    		System.out.println(x);
	    	}else {
	    		auteursToAdd.add(x);
	    	}
	    });
	    
	    bookform.getBook().setAuteur(auteursToAdd);
	    repository.save(bookform.getBook());

//	    repository.save(book);
	    return "redirect:/book";
	}
	
}
