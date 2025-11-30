package tn.vhs.service;

import java.util.List;

import tn.vhs.dto.RentalCreateDto;
import tn.vhs.dto.RentalDto;
import tn.vhs.dto.RentalReturnDto;
import tn.vhs.model.Rental;

public interface RentalService {

	public abstract List<RentalDto> getRentals();

	public abstract RentalDto getRental(Long id);

	public abstract RentalDto rentVhs(RentalCreateDto dto);

	public abstract RentalDto returnVhs(Long id, RentalReturnDto dto);

	public abstract RentalDto updateRental(Long id, RentalDto dto);

	public abstract void deleteRental(Long id);
	
	public abstract Rental findById(Long id);

}
