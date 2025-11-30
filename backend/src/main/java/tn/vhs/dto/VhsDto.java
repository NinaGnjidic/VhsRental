package tn.vhs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VhsDto {
	private Long id;

	@NotBlank(message = "{title.notempty}")
	private String title;

	@NotBlank(message = "{genre.notempty}")
	private String genre;

	@NotNull(message = "{releaseYear.notempty}")
	private Integer releaseYear;

	public VhsDto(Long id, String title, String genre, Integer releaseYear) {
		this.id = id;
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

	public String getGenre() {
		return genre;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}

}
