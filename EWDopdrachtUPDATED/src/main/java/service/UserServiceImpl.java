package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import domain.Users;
import exception.DuplicateUserException;
import exception.UserNotFoundException;
import repository.UserRepository;

public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public Users getUser(String username) {
		return userRepo.findById(username).get();
		
	}

	@Override
	public List<Users> getAllUsers() {
		
		Iterable<Users> source = userRepo.findAll();
		List<Users> users = new ArrayList<>();
		source.forEach(users::add);
		return users;
	}

	@Override
	public Users createUser(Users user) {
		
		if(userRepo.findById(user.getUsername()) !=null) {
			throw new DuplicateUserException(user.getUsername());
		}
		userRepo.save(user);
		return user;
		
		
	}

	@Override
	public Users deleteUser(String username) {
		Optional<Users> user = userRepo.findById(username);
		if(user.get() ==null) {
			throw new UsernameNotFoundException(username);
		}
		userRepo.delete(user.get());
		return user.get();
	}

	@Override
	public Users updateUser(Users user) {
		Optional<Users> userFound = userRepo.findById(user.getUsername());
		if(userFound.get() ==null) {
			throw new UserNotFoundException(user.getUsername());
		}
		userFound.get().setPassword(user.getPassword());
		userFound.get().setUsername(user.getPassword());
		userFound.get().setMaxFavourites(user.getMaxFavourites());
		userRepo.save(userFound.get());
		return userFound.get();
	}
	
	

}
