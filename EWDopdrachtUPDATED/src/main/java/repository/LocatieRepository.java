package repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import domain.Locatie;

public interface LocatieRepository extends CrudRepository<Locatie, Long> {

	Optional<Locatie> findByPlaatsnaamAndPlaatscode1AndPlaatscode2(String plaatsnaam, Integer plaatscode1,Integer plaatscode2);

}
