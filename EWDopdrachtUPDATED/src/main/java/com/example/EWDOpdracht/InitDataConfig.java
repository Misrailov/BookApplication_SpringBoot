package com.example.EWDOpdracht;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import domain.Admin;
import domain.Auteur;
import domain.Book;
import domain.Locatie;
import repository.AdminRepository;
import repository.BookRepository;

@Component
public class InitDataConfig implements CommandLineRunner{


	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private BookRepository bookRepository;
	
	
	@Override
	public void run(String...args) {
		adminRepository.save(new Admin("Musa ","Israilov", "wachtwoord"));
		adminRepository.save(new Admin("Dada","Baba", "hallo"));
		adminRepository.save(new Admin("Karina","Ibisheva", "hallo"));
		
        List<Book> books = new ArrayList<>();

        // Create the first book
        List<Auteur> book1Authors = new ArrayList<>();
        List<Locatie> locaties = new ArrayList<>();
        locaties.add(new Locatie(230,300, "Oostende"));
        book1Authors.add(new Auteur("J.R.R." , " Tolkien"));
        Book book1 = new Book("9788845269707", book1Authors, "The Lord of the Rings", 20.00, 4, locaties);
        books.add(book1);

        // Create the second book
        List<Auteur> book2Authors = new ArrayList<>();
        List<Locatie> locaties3 = new ArrayList<>();
        locaties3.add(new Locatie(230,300, "Oostende2"));
        book2Authors.add(new Auteur("Gabriel Garcia ", "Marquez"));
        Book book2 = new Book("9780241968581", book2Authors, "One Hundred Years of Solitude", 18.99, 5,locaties3);
        books.add(book2);

        // Create the third book
        List<Auteur> book3Authors = new ArrayList<>();

         List<Locatie> locaties2 = new ArrayList<>();
         locaties2.add(new Locatie(230,300, "Oostende2"));
        book3Authors.add(new Auteur("Harpe", "Lee"));
        Book book3 = new Book("9780446310789", book3Authors, "To Kill a Mockingbird", 9.99, 4,locaties2);
        books.add(book3);
        for(Book book: books) {
        	bookRepository.save(book);
        }
	}
}
