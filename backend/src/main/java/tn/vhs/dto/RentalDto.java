package tn.vhs.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentalDto {
	private Long id;

	private VhsDto vhs;

	private UserDto user;

	private LocalDate rentalDate;

	private LocalDate dueDate;

	private LocalDate returnDate;

	private BigDecimal lateFee;

	public RentalDto(Long id, VhsDto vhs, UserDto user, LocalDate rentalDate, LocalDate dueDate, LocalDate returnDate,
			BigDecimal lateFee) {
		this.id = id;
		this.vhs = vhs;
		this.user = user;
		this.rentalDate = rentalDate;
		this.dueDate = dueDate;
		this.returnDate = returnDate;
		this.lateFee = lateFee;
	}

	public Long getId() {
		return id;
	}

	public VhsDto getVhs() {
		return vhs;
	}

	public UserDto getUser() {
		return user;
	}

	public LocalDate getRentalDate() {
		return rentalDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public BigDecimal getLateFee() {
		return lateFee;
	}

}
