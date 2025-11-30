package tn.vhs.service;

import java.util.List;

import tn.vhs.dto.VhsDto;
import tn.vhs.model.Vhs;

public interface VhsService {

	public abstract List<VhsDto> getVhs(String genre, Integer releaseYear);
	
	public abstract List<String> getGenres();
	
	public abstract List<Integer> getReleaseYears();
	
	public abstract VhsDto getVhs(Long id);

	public abstract VhsDto createVhs(VhsDto dto);

	public abstract VhsDto updateVhs(Long id, VhsDto dto);

	public abstract void deleteVhs(Long id);
	
	public abstract Vhs findById(Long id);

}
