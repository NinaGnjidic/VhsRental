package tn.vhs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tn.vhs.dto.RentalCreateDto;
import tn.vhs.dto.RentalDto;
import tn.vhs.dto.RentalReturnDto;
import tn.vhs.service.RentalService;

@RestController
@RequestMapping("/rentals")
public class RentalController {

	@Autowired
	RentalService service;

	@GetMapping("")
	public List<RentalDto> getRentals() {
		return service.getRentals();
	}

	@GetMapping("/{id}")
	public RentalDto getRental(@PathVariable Long id) {
		return service.getRental(id);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public RentalDto rentVhs(@Valid @RequestBody RentalCreateDto rental) {
		return service.rentVhs(rental);
	}

	@PatchMapping("/{id}")
	public RentalDto returnVhs(@PathVariable Long id, @Valid @RequestBody RentalReturnDto rental) {
		return service.returnVhs(id, rental);
	}

	@PutMapping("/{id}")
	public RentalDto updateRental(@PathVariable Long id, @Valid @RequestBody RentalDto rental) {
		return service.updateRental(id, rental);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRental(@PathVariable Long id) {
		service.deleteRental(id);
	}

}
