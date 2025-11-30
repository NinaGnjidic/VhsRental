package tn.vhs.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Rental {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rental_seq_gen")
    @SequenceGenerator(name = "rental_seq_gen", sequenceName = "rental_id_seq", allocationSize = 1)
	private Long id;

	@Column
	private LocalDate rentalDate;

	@Column
	private LocalDate dueDate;

	@Column
	private LocalDate returnDate;

	@ManyToOne
	private Vhs vhs;

	@ManyToOne
	private User user;

	public Rental() {
	}

	public Rental(Vhs vhs, User user, LocalDate rentalDate, LocalDate dueDate) {
		this.vhs = vhs;
		this.user = user;
		this.rentalDate = rentalDate;
		this.dueDate = dueDate;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(LocalDate rentalDate) {
		this.rentalDate = rentalDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public Vhs getVhs() {
		return vhs;
	}

	public void setVhs(Vhs vhs) {
		this.vhs = vhs;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
