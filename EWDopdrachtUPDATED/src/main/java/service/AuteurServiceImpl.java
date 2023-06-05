package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import domain.Auteur;
import exception.AuthorNotFoundException;
import exception.DuplicateAuthorException;
import repository.AuteurRepository;

public class AuteurServiceImpl implements AuteurService{
	
	@Autowired 
	private AuteurRepository auteurRepo;

	@Override
	public Auteur getAuteur(Long id) {
		Optional<Auteur> auteur = auteurRepo.findById(id);
		return auteur.get();
	}

	@Override
	public List<Auteur> getAllAteurs() {
		Iterable<Auteur> source = auteurRepo.findAll();
		List<Auteur> auteurs = new ArrayList<>();
		source.forEach(auteurs::add);
		return auteurs;
	}

	@Override
	public Auteur createAuteur(Auteur auteur) {
		if(auteurRepo.findById(auteur.getId()) != null) {
			 throw new DuplicateAuthorException(auteur.getNaam() + auteur.getFirstname());
			
		}
		auteurRepo.save(auteur);
		return auteur;
	}

	@Override
	public Auteur deleteAuteur(Long id) {
		Optional<Auteur> auteur = auteurRepo.findById(id);
		if(auteur.get() ==null) {
			 throw new AuthorNotFoundException(auteur.get().getId());
			
		}
		auteurRepo.delete(auteur.get());
		return auteur.get();
	}

	@Override
	public List<Auteur> getAuteurFromLastName(String lastName) {
		List<Auteur> auteurs = auteurRepo.findByNaam(lastName);
		return auteurs;
	}

	@Override
	public List<Auteur> getAuteurFromFirstName(String FirstName) {
		List<Auteur> auteurs = auteurRepo.findByFirstname(FirstName);
		return auteurs;
	}

	@Override
	public Iterable<Auteur> addAllAuteurs(Set<Auteur> auteurs) {
		// TODO Auto-generated method stub
		return auteurRepo.saveAll(auteurs);
	}

	@Override
	public Auteur findByNaamAndFirstname(String naam, String firstname) {
		// TODO Auto-generated method stub
		return auteurRepo.findByNaamAndFirstname(naam, firstname).get();
	}

}
