package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import domain.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long> {
	
	List<Admin> findByName(String name);
	List<Admin> findByFirstName(String firstName);
	

}
