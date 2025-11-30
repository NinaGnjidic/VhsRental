package tn.vhs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.vhs.dto.Mapper;
import tn.vhs.dto.VhsDto;
import tn.vhs.exceptions.NoResourceException;
import tn.vhs.model.Vhs;
import tn.vhs.repository.VhsRepository;
import tn.vhs.service.VhsService;

@Service
public class VhsServiceImpl implements VhsService {

	@Autowired
	VhsRepository repository;

	@Autowired
	private Mapper mapper;

	@Override
	public List<VhsDto> getVhs(String genre, Integer releaseYear) {
		if (genre == null && releaseYear == null)
			return repository.findAll().stream().map(mapper::toDto).toList();
		else
			return repository.findByGenreAndReleaseYear(genre, releaseYear).stream().map(mapper::toDto).toList();
	}

	@Override
	public List<String> getGenres() {
		return repository.findDistinctGenres();
	}

	@Override
	public List<Integer> getReleaseYears() {
		return repository.findDistinctReleaseYears();
	}

	@Override
	public VhsDto getVhs(Long id) {
		Vhs vhs = findById(id);

		return mapper.toDto(vhs);
	}

	@Override
	public VhsDto createVhs(VhsDto dto) {
		Vhs vhs = mapper.toVhs(dto);
		Vhs createdVhs = repository.save(vhs);

		return mapper.toDto(createdVhs);
	}

	@Override
	public VhsDto updateVhs(Long id, VhsDto dto) {
		Vhs vhs = findById(id);

		vhs.setTitle(dto.getTitle());
		vhs.setGenre(dto.getGenre());
		vhs.setReleaseYear(dto.getReleaseYear());

		Vhs updatedVhs = repository.save(vhs);

		return mapper.toDto(updatedVhs);
	}

	@Override
	public void deleteVhs(Long id) {
		Vhs vhs = findById(id);
		repository.delete(vhs);
	}

	@Override
	public Vhs findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new NoResourceException("vhs.notfound"));
	}

}
