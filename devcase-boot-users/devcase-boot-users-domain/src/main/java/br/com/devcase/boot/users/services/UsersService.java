package br.com.devcase.boot.users.services;

public interface UsersService {

	void changePassword(String username, String oldpassword, String newpassword);
}
