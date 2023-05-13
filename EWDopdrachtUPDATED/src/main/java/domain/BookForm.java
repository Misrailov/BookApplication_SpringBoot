package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BookForm {
	private Book book;
	
	//set en return unmodifiable collection
	private Set<Auteur> auteurs = new HashSet();
	public Book getBook() {
		return this.book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Set<Auteur>  getAuteurs() {
		if(auteurs.isEmpty()) {
			auteurs.add(new Auteur("Last Name 1", "First Name 1"));
			auteurs.add(new Auteur("Last Name 2", "First Name 2"));
			auteurs.add(new Auteur("Last Name 3", "First Name 3"));
		}
		return Collections.unmodifiableSet(auteurs);
	}
	public void setAuteurs(Set<Auteur> auteurs) {
		this.auteurs = auteurs;
	}
	

}
