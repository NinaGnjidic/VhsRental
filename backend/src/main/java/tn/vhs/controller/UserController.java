package tn.vhs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tn.vhs.dto.UserDto;
import tn.vhs.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService service;

	@GetMapping("")
	public List<UserDto> getUsers() {
		return service.getUsers();
	}

	@GetMapping("/{id}")
	public UserDto getUser(@PathVariable Long id) {
		return service.getUser(id);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto createUser(@Valid @RequestBody UserDto user) {
		return service.createUser(user);
	}

	@PutMapping("/{id}")
	public UserDto updateUser(@PathVariable Long id, @Valid @RequestBody UserDto user) {
		return service.updateUser(id, user);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Long id) {
		service.deleteUser(id);
	}

}
