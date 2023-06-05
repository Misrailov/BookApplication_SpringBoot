package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import domain.Favourite;
import exception.DuplicateFavouriteException;
import exception.FavouriteLimitExceededException;
import exception.FavouriteNotFoundException;
import repository.FavouriteRepository;

public class FavouriteServiceImpl implements FavouriteService {
	
	@Autowired
	private FavouriteRepository favRepo;

	@Override
	public Favourite getFavourite(Long id) {
		Optional<Favourite> fav = favRepo.findById(id);
		return fav.get();
	}

	@Override
	public List<Favourite> getAllFavourites() {
		Iterable<Favourite> source = favRepo.findAll();
		List<Favourite> favs = new ArrayList<>();
		source.forEach(favs::add);
		return favs;
	}

	@Override
	public Favourite createFavourite(Favourite favourite) {
		Iterable<Favourite> source = favRepo.findAll();
		for(Favourite fav: source ) {
			if((fav.getISBNNummer() == favourite.getISBNNummer()) && (fav.getNaam() == favourite.getNaam()) ) {
				throw new DuplicateFavouriteException(fav.getId());
			}
		}
		favRepo.save(favourite);
		return favourite;
	}

	@Override
	public Favourite deleteFavourite(Long id) {
		Optional<Favourite> fav = favRepo.findById(id);
		if(fav.get() ==null) {
			throw new FavouriteNotFoundException(fav.get().getId());
		}
		favRepo.delete(fav.get());
		return fav.get();
	}

	@Override
	public List<Favourite> getFavouritesFromUser(String naam) {
		List<Favourite> favs = favRepo.findByNaam(naam);
		return favs;
	}

	@Override
	public List<Favourite> getFavouritesFromBook(String ISBNNumber) {
		List<Favourite> favs = favRepo.findByISBNNummer(ISBNNumber);
		return favs;
		
	}

	@Override
	public Iterable<Favourite> addAllFavourites(Set<Favourite> favouritesToAdd) {
		// TODO Auto-generated method stub
		return favRepo.saveAll(favouritesToAdd);
	}

	@Override
	public Favourite findByISBNNummerAndNaam(String ISBNNummer, String naam) {
		// TODO Auto-generated method stub
		return favRepo.findByISBNNummerAndNaam(ISBNNummer, naam);
	}

}
