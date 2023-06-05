package service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import domain.Auteur;

public interface AuteurService {
	
	public Auteur getAuteur(Long id);
	public List<Auteur> getAllAteurs();
	public Auteur createAuteur(Auteur auteur);
	public Auteur deleteAuteur(Long id);
	public List<Auteur> getAuteurFromLastName(String lastName);
	public List<Auteur> getAuteurFromFirstName(String FirstName);
	public Iterable<Auteur> addAllAuteurs(Set<Auteur> auteursToAdd);
	Auteur findByNaamAndFirstname(String naam, String firstname);

	

}
