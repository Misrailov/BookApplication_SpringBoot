package com.example.EWDOpdracht;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import domain.Book;
import domain.Favourite;
import repository.BookRepository;
import service.BookService;

@Controller
@RequestMapping("/")
public class HomeController {

	private WebClient webClient = WebClient.create();
	private final String SERVER_URI = "http://localhost:8080/rest";
	@Autowired
	BookService bookService;

	@GetMapping(value = "/admin")
	public String adminPage(Model model) {
		return "admin";
	}

	@GetMapping(value = "/search")
	public String searchPage(Model model) {

		model.addAttribute("list", bookService.getAllBooks());

		return "search";
	}

	@PostMapping(value =  "/search" )
	public String home(Book book, Model model, String keyword, @RequestParam("nameSearch") boolean nameSearchParam) {
		try {
		if (!nameSearchParam) {
			
			if (keyword.contains(" ")) {
				String firstName = keyword.split(" ")[0];
				String name = keyword.split(" ")[1];
				List<Book> books = webClient.get().uri(SERVER_URI + "/author/" + name + "_" + firstName).retrieve()
						.bodyToFlux(Book.class).collectList().block();
				if (books.isEmpty()) {
					model.addAttribute("list", bookService.getAllBooks());
					return "search";
				} else {
				model.addAttribute("list",books);
				return "search";
			}
				}

			Book bookByISBN = webClient.get().uri(SERVER_URI + "/ISBN/" + keyword).retrieve().bodyToMono(Book.class).block();
					
			if (bookByISBN !=null) {
				return "redirect:/book/" + bookByISBN.getId();
			} else {
				model.addAttribute("list", bookService.getAllBooks());
			}
			return "search";
		}

		if (keyword != null) {
			List<Book> list = bookService.findByNaam(keyword);

			if (list.isEmpty()) {
				List<Book> books = webClient.get().uri(SERVER_URI + "/books").retrieve().bodyToFlux(Book.class)
						.collectList().block();
				List<Book> newList = books.stream()
						.filter(x -> x.getNaam().toLowerCase().contains(keyword.toLowerCase())).toList();
				model.addAttribute("list", newList);

			} else {
				model.addAttribute("list", list);
			}
		} else {
			model.addAttribute("list", bookService.getAllBooks());
		}
		}catch(Exception e) {
			System.out.println(e);
		}
		return "search";
	}

	@GetMapping(value = "/home")
	public String home(Model model, Principal principal,@ModelAttribute("notification") String notification) {
		List<Book> books = webClient.get().uri(SERVER_URI + "/books").retrieve().bodyToFlux(Book.class).collectList()
				.block();

		List<String> allFavouritesFromUser = webClient.get().uri(SERVER_URI + "/favourite").retrieve()
				.bodyToFlux(Favourite.class).collectList().block().stream()
				.filter(x -> x.getNaam().equals(principal.getName())).map(x -> x.getISBNNummer()).toList();

		List<Book> top3Books = books.stream().sorted(Comparator.comparingInt(Book::getAantalSterren).reversed()
				.thenComparing(Comparator.comparing(Book::getNaam))).limit(3).collect(Collectors.toList());

		List<Book> favouriteBooks = books.stream().filter(x -> allFavouritesFromUser.contains(x.getISBNNumber())).sorted(Comparator.comparing(Book::getNaam))
				.toList();
		model.addAttribute("favBooks", favouriteBooks);
		model.addAttribute("popularBooks", top3Books);
		if(notification !=null) {
			model.addAttribute("notification", notification);
		}
		return "home";
	}
}
