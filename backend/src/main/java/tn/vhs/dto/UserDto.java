package tn.vhs.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDto {
	private Long id;
	
	@NotBlank(message = "{name.notempty}")
	private String name;
	
	@Email(message = "{email.notvaild}")
	@NotBlank(message = "{email.notempty}")
	private String email;
	
	public UserDto(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

}
