package tn.vhs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.vhs.dto.Mapper;
import tn.vhs.dto.UserDto;
import tn.vhs.exceptions.NoResourceException;
import tn.vhs.model.User;
import tn.vhs.repository.UserRepository;
import tn.vhs.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;

	@Autowired
	private Mapper mapper;

	@Override
	public List<UserDto> getUsers() {
		return repository.findAll().stream().map(mapper::toDto).toList();
	}

	@Override
	public UserDto getUser(Long id) {
		User user = findById(id);

		return mapper.toDto(user);
	}

	@Override
	public UserDto createUser(UserDto dto) {
		User user = mapper.toUser(dto);
		User createdUser = repository.save(user);

		return mapper.toDto(createdUser);
	}

	@Override
	public UserDto updateUser(Long id, UserDto dto) {
		User user = findById(id);

		user.setName(dto.getName());
		user.setEmail(dto.getEmail());

		User updatedUser = repository.save(user);

		return mapper.toDto(updatedUser);
	}

	@Override
	public void deleteUser(Long id) {
		User user = findById(id);
		repository.delete(user);
	}

	@Override
	public User findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new NoResourceException("user.notfound"));
	}

}
