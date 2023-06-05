package repository;

import org.springframework.data.repository.CrudRepository;

import domain.Users;

public interface UserRepository extends CrudRepository<Users, String> {

}
