package tn.vhs.service;

import java.util.List;

import tn.vhs.dto.UserDto;
import tn.vhs.model.User;

public interface UserService {

	public abstract List<UserDto> getUsers();

	public abstract UserDto getUser(Long id);

	public abstract UserDto createUser(UserDto user);

	public abstract UserDto updateUser(Long id, UserDto user);

	public abstract void deleteUser(Long id);

	public abstract User findById(Long id);
}
