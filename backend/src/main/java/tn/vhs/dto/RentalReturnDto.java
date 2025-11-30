package tn.vhs.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public class RentalReturnDto {

	@NotNull(message = "{returnDate.notempty}")
    @PastOrPresent(message = "{returnDate.pastOrPresent}")
	private LocalDate returnDate;

	public LocalDate getReturnDate() {
		return returnDate;
	}

}
