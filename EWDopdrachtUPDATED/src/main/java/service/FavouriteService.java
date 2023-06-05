package service;

import java.util.List;
import java.util.Set;

import domain.Auteur;
import domain.Favourite;

public interface FavouriteService {
	
	public Favourite getFavourite(Long id);
	public List<Favourite> getAllFavourites();
	public Favourite createFavourite(Favourite favourite);
	public Favourite deleteFavourite(Long id);
	public List<Favourite> getFavouritesFromUser(String naam);
	public List<Favourite> getFavouritesFromBook(String ISBNNumber);
	public Iterable<Favourite> addAllFavourites(Set<Favourite> favouritesToAdd);
	Favourite findByISBNNummerAndNaam(String ISBNNummer, String naam);
	
}
