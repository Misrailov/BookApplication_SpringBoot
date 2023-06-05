package com.example.EWDOpdracht;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import domain.Auteur;
import domain.Book;
import domain.Locatie;
import service.AuteurService;
import service.BookService;
import service.LocatieService;

@Component
public class InitDataConfig implements CommandLineRunner {

	@Autowired
	BookService bookService;
	@Autowired
	AuteurService auteurService;
	@Autowired
	LocatieService locatieService;

	@Override
	public void run(String...args) {
//		
//		Optional<Users> user = userRepo.findById("user");
//		Optional<Users> admin = userRepo.findById("admin");
//		user.get().setMaxFavourites(4);
//		admin.get().setMaxFavourites(7);
//		userRepo.save(user.get());
//		userRepo.save(admin.get());
//		Set<Auteur> auteursToAdd = new HashSet<>();
//		Set<Locatie> locatiesToAdd = new HashSet<>();
//		
//		auteursToAdd.add(new Auteur("King", "Stephen"));
//		locatiesToAdd.add(new Locatie(100,200,"America"));
//		
//		locatieService.addAllLocaties(locatiesToAdd);
//		auteurService.addAllAuteurs(auteursToAdd);
//		
//		String ISBN = "978-1444707823";
//		String cover = "https://m.media-amazon.com/images/I/41yAvDa1yBL._SX324_BO1,204,203,200_.jpg";
//		String naam = "Cell: Stephen King (Epic thriller)";
//		double prijs = 9.99;
//		int sterren =177; 
//		Book book =  new Book(ISBN,auteursToAdd,naam , prijs, locatiesToAdd );
//		book.setAantalSterren(sterren);
//		book.setCover(cover);
//		bookService.createBook(book);
		
		
		
		
		
		
	}
}
