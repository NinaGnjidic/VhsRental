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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tn.vhs.dto.VhsDto;
import tn.vhs.service.VhsService;

@RestController
@RequestMapping("/vhs")
public class VhsController {

	@Autowired
	VhsService service;

	@GetMapping("")
	public List<VhsDto> getVhs(@RequestParam(required = false) String genre, @RequestParam(required = false) Integer releaseYear) {
		return service.getVhs(genre, releaseYear);
	}
	
	@GetMapping("/genres")
	public List<String> getGenres() {
		return service.getGenres();
	}
	
	@GetMapping("/releaseYears")
	public List<Integer> getReleaseYears() {
		return service.getReleaseYears();
	}
	
	@GetMapping("/{id}")
	public VhsDto getVhs(@PathVariable Long id) {
		return service.getVhs(id);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public VhsDto createVhs(@Valid @RequestBody VhsDto vhs) {
		return service.createVhs(vhs);
	}

	@PutMapping("/{id}")
	public VhsDto updateVhs(@PathVariable Long id, @Valid @RequestBody VhsDto vhs) {
		return service.updateVhs(id, vhs);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteVhs(@PathVariable Long id) {
		service.deleteVhs(id);
	}
}
