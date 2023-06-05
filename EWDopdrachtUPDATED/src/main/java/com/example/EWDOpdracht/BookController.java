package com.example.EWDOpdracht;

import java.security.Principal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

import domain.Auteur;
import domain.Book;
import domain.BookForm;
import domain.Favourite;
import domain.Locatie;
import domain.Users;
import jakarta.validation.Valid;
import repository.AuteurRepository;
import repository.BookRepository;
import repository.FavouriteRepository;
import repository.LocatieRepository;
import repository.UserRepository;
import service.AuteurService;
import service.BookService;
import service.FavouriteService;
import service.LocatieService;
import service.UserService;
import utility.Message;

@Controller
@RequestMapping("/book")
public class BookController {
	private WebClient webClient = WebClient.create();

	@Autowired
	private MessageSource messageSource;

	//@Autowired
	//private FavouriteRepository favRepo;

	@Autowired
	AuteurService auteurService;
	
	
	
	@Autowired
	private LocatieService locatieService;
	
	@Autowired
	private UserService userService;
	@Autowired
	BookService bookService;
	@Autowired 
	FavouriteService favouriteService;
	

	

	private final String SERVER_URI = "http://localhost:8080/rest";

	@GetMapping
	public String listBook(Model model) {
		
		model.addAttribute("bookList", bookService.getAllBooks());
//		model.addAttribute("bookList", repository.findAll());
		// model.addAttribute("bookAuthor", repository.findByAuteurs("J.R.R. Tolkien"));
		return "book";
	}
	@GetMapping(value = "/short")
	public String listBookShort(Model model) {

		model.addAttribute("bookList", bookService.getAllBooks().stream().sorted(Comparator.comparing(Book::getAantalSterren).reversed()).toArray());
		return "bookshort";
	}


	

	@GetMapping(value = "/new")
	public String createForm(Model model) throws JsonProcessingException {
		BookForm bookForm = new BookForm();

		model.addAttribute("bookForm", bookForm);
//		model.addAttribute("book", bookForm.getBook())

		return "/new";

	}

	@GetMapping(value = "/{id}")
	public String showBook(@PathVariable("id") Long id, Model model,Principal principal) {
		
		try {
			Users user = userService.getUser(principal.getName());
			
		Book book = webClient.get().uri(SERVER_URI + "/book/" + id).retrieve().bodyToMono(Book.class).block();
		List<Favourite> favourites = webClient.get().uri(SERVER_URI + "/favourite").retrieve().bodyToFlux(Favourite.class).collectList().block();
		long amountOfFavourites = favourites.stream().filter(x->x.getNaam().equals(principal.getName())).count();
		boolean isFavourited = !favourites.stream().filter(x -> x.getISBNNummer().equals(book.getISBNNumber())&& x.getNaam().equals(principal.getName())).toList().isEmpty();
		boolean MaxFavouritesReached = amountOfFavourites >= user.getMaxFavourites(); 
		String maxReachedText = "Max favourites of " + user.getMaxFavourites() + " books reached.";
		model.addAttribute("maxReachedText", maxReachedText);
		model.addAttribute("maxReached", MaxFavouritesReached);
		model.addAttribute("book", book);
		model.addAttribute("isFavourited", isFavourited);
		model.addAttribute("notifications", null);
		return "/bookShow";
		}catch(Exception e) {
			System.out.println(e);
			return "redirect:/book";
		}
		

	}
@GetMapping(value = "/update/{id}")
public String updateBookForm(Model model, @PathVariable("id") Long id, Principal principal) {
	BookForm bookForm = new BookForm();
	
	Book book = bookService.getBook(id);
	int count = 0;
	for(Auteur auteur: book.getAuteurs()) {
		if(count ==0) {
			bookForm.setFirstAuthor(auteur.getNaam());
			bookForm.setFirstAuthorName(auteur.getFirstname());
			count++;
		}else if(count ==1) {
			bookForm.setSecondAuthor(auteur.getNaam());
			bookForm.setSecondAuthorName(auteur.getFirstname());
			count++;
		}else if(count ==2) {
			bookForm.setThirdAuthor(auteur.getNaam());
			bookForm.setThirdAuthorName(auteur.getFirstname());
		}
	}
	count = 0;
	for(Locatie locatie: book.getLocaties()) {
		if(count ==0) {
			bookForm.setFirstLocation(locatie.getPlaatsnaam());
			bookForm.setFirstLocationfirstcode(locatie.getPlaatscode1());
			bookForm.setFirstLocationsecondCode(locatie.getPlaatscode2());
		
			count++;
		}else if(count ==1) {
			bookForm.setSecondLocation(locatie.getPlaatsnaam());
			bookForm.setSecondLocationfirstcode(locatie.getPlaatscode1());
			bookForm.setSecondLocationsecondCode(locatie.getPlaatscode2());
		
		
			count++;
		}else if(count ==2) {
			bookForm.setThirdLocation(locatie.getPlaatsnaam());
			bookForm.setThirdLocationfirstcode(locatie.getPlaatscode1());
			bookForm.setThirdLocationsecondCode(locatie.getPlaatscode2());
		
		
			count++;
		}
	}
	count =0;
	bookForm.setBook(book);
	model.addAttribute("bookForm", bookForm);
	return "/change";
	
}

	@PostMapping(value = "/{id}")
	public String addToFavorites(@PathVariable("id") Long id,Model model, Principal principal,@RequestParam("isFavourited") boolean isFavourited,RedirectAttributes redirectAttributes) {

		Book book = bookService.getBook(id);
		if(!isFavourited) {
		
		book.setAantalSterren(book.getAantalSterren() + 1);
		Favourite favExists = favouriteService.findByISBNNummerAndNaam( book.getISBNNumber(),principal.getName());

		if(favExists ==null) {
		favouriteService.createFavourite(new Favourite(principal.getName(), book.getISBNNumber()));
		}
		String notification = "Added book to favourites";
		
		redirectAttributes.addFlashAttribute("notification", notification);//			model.addAttribute("isFavourited", !isFavourited);
		
//		model.addAttribute("isFavourited", !isFavourited);
//		model.addAttribute("book", book.get());
		return "redirect:/home";
		}
		else {
			book.setAantalSterren(book.getAantalSterren() - 1);
			Favourite toDeleteFav = favouriteService.findByISBNNummerAndNaam( book.getISBNNumber(),principal.getName());;
			favouriteService.deleteFavourite(toDeleteFav.getId());
			String notification = "Removed book from favourites";
			redirectAttributes.addFlashAttribute("notification", notification);//			model.addAttribute("isFavourited", !isFavourited);
//			model.addAttribute("book", book.get());
			return "redirect:/home";
			
		}
		//repository.save(book.get());
		//return "/bookShow";
		//return "redirect:/book/{id}";
	}
	
	@PostMapping(value = "/new")
	public String create(@Valid BookForm bookForm, BindingResult result, Model model, Locale locale,@RequestParam(required = false) boolean update)
			throws JsonProcessingException {
		boolean ErrorExists = false;


	
		if (result.hasErrors()) {
			

			model.addAttribute("message",
					new Message("error", messageSource.getMessage("book_save_fail", new Object[] {}, locale)));
			model.addAttribute("errorMessage",
					new Message("error", messageSource.getMessage("book_save_fail", new Object[] {}, locale)).getMessage());
			ErrorExists = true;
		}

		//defining Authors
		Set<Auteur> auteursToAdd = new HashSet<>();
		Auteur firstAuteur = new Auteur(bookForm.getFirstAuthor(), bookForm.getFirstAuthorName());
		Optional<Book> bookExists =bookService.findByISBNOptional(bookForm.getBook().getISBNNumber());

		if(!bookExists.isEmpty() && !update) {
			model.addAttribute("message6",
					new Message("error", messageSource.getMessage("book_save_fail", new Object[] {}, locale)));
			model.addAttribute("errorMessage6",
					new Message("error", messageSource.getMessage("bookExistsError", new Object[] {}, locale)).getMessage());

			ErrorExists = true;
			
		}
		
		auteursToAdd.add(firstAuteur);
		if(!bookForm.getSecondAuthor().isBlank()||!bookForm.getSecondAuthor().isEmpty()) {
			
			if(!bookForm.getSecondAuthorName().isBlank()||!bookForm.getSecondAuthorName().isEmpty()) {
				
				Auteur secondAuteur = new Auteur(bookForm.getSecondAuthor(), bookForm.getSecondAuthorName());
				auteursToAdd.add(secondAuteur);
			}else {
				
				result.addError(new ObjectError("secondAuthor", "add a last name"));
				model.addAttribute("message2",
						new Message("error", messageSource.getMessage("book_save_fail", new Object[] {}, locale)));
				model.addAttribute("errorMessage2",
						new Message("error", messageSource.getMessage("FirstNameNeedsLastName", new Object[] {}, locale)).getMessage());

				ErrorExists = true;

			}
		}
		if(!bookForm.getThirdAuthor().isBlank()||!bookForm.getThirdAuthor().isEmpty() ) {
			if(!bookForm.getThirdAuthorName().isBlank()||!bookForm.getThirdAuthorName().isEmpty()) {
				Auteur thirdAuteur = new Auteur(bookForm.getThirdAuthor(), bookForm.getThirdAuthorName());
				auteursToAdd.add(thirdAuteur);
				
			}else {

				result.addError(new ObjectError("thirdAuthor", "add a last name"));

				model.addAttribute("message3",
						new Message("error", messageSource.getMessage("book_save_fail", new Object[] {}, locale)));
				model.addAttribute("errorMessage3",
						new Message("error", messageSource.getMessage("FirstNameNeedsLastName", new Object[] {}, locale)).getMessage());

				ErrorExists = true;


			}
		}
		
		//defining Locations
		Set<Locatie> locatiesToAdd = new HashSet<>();
		if(bookForm.getFirstLocation().isBlank()||bookForm.getFirstLocation().isEmpty() || bookForm.getFirstLocation() ==null ||
				bookForm.getFirstLocationsecondCode() ==null ||bookForm.getFirstLocationsecondCode() ==null
				||bookForm.getFirstLocationfirstcode() ==null ||bookForm.getFirstLocationsecondCode() ==null) {
			ErrorExists = true;
			
		}else {
			locatiesToAdd.add(new Locatie(bookForm.getFirstLocationfirstcode(),bookForm.getFirstLocationsecondCode(),bookForm.getFirstLocation()));

		}

		if(!bookForm.getSecondLocation().isBlank()||!bookForm.getSecondLocation().isEmpty()) {
			if(bookForm.getSecondLocationfirstcode() ==null ||bookForm.getSecondLocationsecondCode() ==null) {
				
				result.addError(new ObjectError("thirdAuthor", "add the place codes to the second location"));

				model.addAttribute("message4",
						new Message("error", messageSource.getMessage("book_save_fail", new Object[] {}, locale)));
				model.addAttribute("errorMessage4",
						new Message("error", messageSource.getMessage("addCodesToForm", new Object[] {}, locale)).getMessage());

				ErrorExists = true;

				
			}else {
				if(bookForm.getSecondLocationfirstcode()-bookForm.getSecondLocationsecondCode() <=49 
						||bookForm.getSecondLocationfirstcode()-bookForm.getSecondLocationsecondCode() >=-49) {
				Locatie locatie = new Locatie(bookForm.getSecondLocationfirstcode(),bookForm.getSecondLocationsecondCode(),bookForm.getSecondLocation());
				locatiesToAdd.add(locatie);
				}else {
					result.addError(new ObjectError("thirdAuthor", "add the place codes to the second location"));

					model.addAttribute("message4",
							new Message("error", messageSource.getMessage("book_save_fail", new Object[] {}, locale)));
					model.addAttribute("errorMessage4",
							new Message("error", messageSource.getMessage("errorCodeDifference", new Object[] {}, locale)).getMessage());

					ErrorExists = true;
				}
			}
			
		}
		if(!bookForm.getThirdLocation().isBlank()||!bookForm.getThirdLocation().isEmpty()) {
			if(bookForm.getThirdLocationfirstcode() ==null ||bookForm.getThirdLocationsecondCode() ==null) {
				result.addError(new ObjectError("thirdAuthor", "add the place codes to the second location"));

				model.addAttribute("message5",
						new Message("error", messageSource.getMessage("book_save_fail", new Object[] {}, locale)));
				model.addAttribute("errorMessage5",
						new Message("error", messageSource.getMessage("addCodesToForm", new Object[] {}, locale)).getMessage());
				ErrorExists = true;

			}else {
				if(bookForm.getThirdLocationfirstcode()-bookForm.getThirdLocationsecondCode() <=49 
						||bookForm.getThirdLocationfirstcode()- bookForm.getThirdLocationsecondCode() >=-49) {
				Locatie locatie = new Locatie(bookForm.getThirdLocationfirstcode(),bookForm.getThirdLocationsecondCode(),bookForm.getThirdLocation());
				locatiesToAdd.add(locatie);
			}else {

				model.addAttribute("message5",
						new Message("error", messageSource.getMessage("book_save_fail", new Object[] {}, locale)));
				model.addAttribute("errorMessage5",
						new Message("error", messageSource.getMessage("errorCodeDifference", new Object[] {}, locale)).getMessage());
				ErrorExists = true;
			}
			
		}
		}

		
		if(bookForm.getFirstLocation().isBlank()||bookForm.getFirstLocation().isEmpty() || bookForm.getFirstLocation() ==null ||
				bookForm.getFirstLocationsecondCode() ==null ||bookForm.getFirstLocationsecondCode() ==null
				||bookForm.getFirstLocationfirstcode() ==null ||bookForm.getFirstLocationsecondCode() ==null) {
			ErrorExists = true;
			
		}else {
			if(bookForm.getFirstLocationfirstcode()-bookForm.getFirstLocationsecondCode() <=49 
					&&bookForm.getFirstLocationfirstcode()-bookForm.getFirstLocationsecondCode() >=-49) {

				model.addAttribute("message7",
						new Message("error", messageSource.getMessage("book_save_fail", new Object[] {}, locale)));
				model.addAttribute("errorMessage7",
						new Message("error", messageSource.getMessage("errorCodeDifference", new Object[] {}, locale)).getMessage());
				ErrorExists = true;
						
					}
		}
			
			
		if(ErrorExists) {
			if(update) {
				return "/change";
			}else {
			return "/new";
			}
		}
	
		
		auteurService.addAllAuteurs(auteursToAdd);
		//locatieRepo.saveAll(locatiesToAdd);
		locatieService.addAllLocaties(locatiesToAdd);
//		auteurService.addAllAuteurs(auteursToAdd);
//		auteurRepo.saveAll(auteursToAdd);
		bookForm.getBook().setAuteur(auteursToAdd);
		bookForm.getBook().setLocaties(locatiesToAdd);
		
		if(update) {
			bookForm.getBook().setAantalSterren(bookExists.get().getAantalSterren());
		}
		
		if(update) {
			bookService.deleteBook(bookExists.get().getId());
//			repository.delete(bookExists.get());
		}
		bookService.createBook(bookForm.getBook());
//		repository.save(bookForm.getBook());
		//return "/new";

		return "redirect:/book";
	}

}
	
