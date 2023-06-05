package service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import domain.Favourite;
import domain.Locatie;

public interface LocatieService {
	
	Locatie findByPlaatsnaamAndPlaatscode1AndPlaatscode2(String plaatsnaam, Integer plaatscode1,Integer plaatscode2);
	Locatie getLocatie(Long id);
	List<Locatie> getAllLocaties();
	Locatie createLocatie(Locatie locatie);
	Locatie deleteLocate(Long id);
	Iterable<Locatie> addAllLocaties(Set<Locatie> locatiesToAdd);

}
