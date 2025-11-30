package tn.vhs.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import tn.vhs.model.Rental;
import tn.vhs.model.User;
import tn.vhs.model.Vhs;

@Component
public class Mapper {//TODO: move?
	
	public UserDto toDto(User user) {
		return new UserDto(user.getId(), user.getName(), user.getEmail());
	}
	
	public User toUser(UserDto dto) {
		return new User(dto.getName(), dto.getEmail());
	}
	
	public VhsDto toDto(Vhs vhs) {
		return new VhsDto(vhs.getId(), vhs.getTitle(), vhs.getGenre(), vhs.getReleaseYear());
	}
	
	public Vhs toVhs(VhsDto dto) {
		return new Vhs(dto.getTitle(), dto.getGenre(), dto.getReleaseYear());
	}
	
	public RentalDto toDto(Rental rental, BigDecimal lateFee) {
		return new RentalDto(rental.getId(), toDto(rental.getVhs()), toDto(rental.getUser()), rental.getRentalDate(), rental.getDueDate(), rental.getReturnDate(), lateFee);
	}

}
