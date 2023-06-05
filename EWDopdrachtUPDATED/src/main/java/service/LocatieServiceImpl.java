package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import domain.Auteur;
import domain.Locatie;
import exception.LocatieNotFoundException;
import repository.LocatieRepository;

public class LocatieServiceImpl implements LocatieService {
	@Autowired
	private LocatieRepository locatieRepository;

	@Override
	public Locatie findByPlaatsnaamAndPlaatscode1AndPlaatscode2(String plaatsnaam, Integer plaatscode1,
			Integer plaatscode2) {
		// TODO Auto-generated method stub
		return locatieRepository.findByPlaatsnaamAndPlaatscode1AndPlaatscode2(plaatsnaam, plaatscode1, plaatscode2).get();
	}

	@Override
	public Locatie getLocatie(Long id) {
		// TODO Auto-generated method stub
		Optional<Locatie> locatieFound = locatieRepository.findById(id);
		if(locatieFound.isEmpty()) {
			throw new LocatieNotFoundException(id);
		}
		return locatieFound.get();
	}

	@Override
	public List<Locatie> getAllLocaties() {
		// TODO Auto-generated method stub
		Iterable<Locatie> source = locatieRepository.findAll();
		List<Locatie> locaties = new ArrayList<>();
		source.forEach(locaties::add);
		return locaties;
	}

	@Override
	public Locatie createLocatie(Locatie locatie) {
		// TODO Auto-generated method stub
		locatieRepository.save(locatie);
		return locatie;
	}

	@Override
	public Locatie deleteLocate(Long id) {
		// TODO Auto-generated method stub
		Optional<Locatie> locatieFound = locatieRepository.findById(id);
		if(locatieFound.isEmpty()) {
			throw new LocatieNotFoundException(id);
		}
		locatieRepository.delete(locatieFound.get());
		return locatieFound.get();
	}

	@Override
	public Iterable<Locatie> addAllLocaties(Set<Locatie> locatiesToAdd) {
		// TODO Auto-generated method stub
		return locatieRepository.saveAll(locatiesToAdd);
	}

}
