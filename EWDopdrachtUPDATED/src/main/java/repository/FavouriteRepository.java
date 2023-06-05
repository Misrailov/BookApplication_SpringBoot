package repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


import domain.Favourite;

public interface FavouriteRepository extends CrudRepository<Favourite, Long> {
	
	List<Favourite> findByNaam(String naam);
	List<Favourite> findByISBNNummer(String ISBNNummer);
	Favourite findByISBNNummerAndNaam(String ISBNNummer, String naam);

}
