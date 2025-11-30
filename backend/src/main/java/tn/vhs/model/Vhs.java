package tn.vhs.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Vhs {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vhs_seq_gen")
    @SequenceGenerator(name = "vhs_seq_gen", sequenceName = "vhs_id_seq", allocationSize = 1)
	private Long id;

	@Column
	private String title;

	@Column
	private String genre;

	@Column
	private int releaseYear;

	@OneToMany(mappedBy = "vhs")
	private List<Rental> rentals = new ArrayList<>();

	public Vhs() {
	}

	public Vhs(String title, String genre, int releaseYear) {
		this.title = title;
		this.genre = genre;
		this.releaseYear = releaseYear;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

}
