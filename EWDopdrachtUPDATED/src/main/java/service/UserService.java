package service;

import java.util.List;

import domain.Users;

public interface UserService {
	
	public Users getUser(String username);
	public List<Users> getAllUsers();
	public Users createUser(Users user);
	public Users deleteUser(String username);
	public Users updateUser(Users user);
	
	

}
