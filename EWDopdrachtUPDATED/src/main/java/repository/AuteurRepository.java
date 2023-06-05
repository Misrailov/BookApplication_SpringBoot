package repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import domain.Auteur;

public interface AuteurRepository extends CrudRepository<Auteur, Long>{

	List<Auteur> findByNaam(String naam);
	List<Auteur> findByFirstname(String firstname);
	Optional<Auteur> findByNaamAndFirstname(String naam, String firstname);
	
	
}
