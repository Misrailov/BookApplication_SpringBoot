package com.example.EWDOpdracht;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import validator.BookValidation;
import domain.Auteur;
import domain.Book;
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
	public String createForm(Model model) {
		Book book = new Book();
		model.addAttribute("book",book);
		model.addAttribute("auteur", new Auteur());
		return "/new";
		
	}
	@PostMapping(value = "/new")
	public String create(@Valid Book book, BindingResult result, Model model, Locale locale) {
		System.out.println(book);
	    if (result.hasErrors()) {
	        model.addAttribute("message", new Message("error", messageSource.getMessage("book_save_fail", new Object[]{}, locale)));
	        return "/new";
	    }

	    repository.save(book);
	    return "redirect:/book";
	}
	
}
