package com.example.EWDOpdracht;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import repository.BookRepository;

@Controller
@RequestMapping("/book")
public class BookController {

	
	@Autowired
	private BookRepository repository;
	
	@GetMapping
	public String listBook(Model model) {
		model.addAttribute("bookList", repository.findAll());
		model.addAttribute("bookName", repository.findByNaam("The Lord of the Rings"));
//		model.addAttribute("bookAuthor", repository.findByAuteurs("J.R.R. Tolkien"));
		return "book";
	}
}
