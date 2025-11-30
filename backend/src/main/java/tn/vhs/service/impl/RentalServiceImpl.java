package tn.vhs.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.vhs.dto.Mapper;
import tn.vhs.dto.RentalCreateDto;
import tn.vhs.dto.RentalDto;
import tn.vhs.dto.RentalReturnDto;
import tn.vhs.exceptions.NoResourceException;
import tn.vhs.exceptions.VhsUnavailableException;
import tn.vhs.model.Rental;
import tn.vhs.model.User;
import tn.vhs.model.Vhs;
import tn.vhs.repository.RentalRepository;
import tn.vhs.service.RentalService;
import tn.vhs.service.UserService;
import tn.vhs.service.VhsService;

@Service
public class RentalServiceImpl implements RentalService {

	@Autowired
	RentalRepository repository;

	@Autowired
	VhsService vhsService;

	@Autowired
	UserService userService;

	@Autowired
	private Mapper mapper;

	@Override
	public List<RentalDto> getRentals() {
		return repository.findAll().stream().map(r -> mapper.toDto(r, calculateLateFee(r))).toList();
	}

	@Override
	public RentalDto getRental(Long id) {
		Rental rental = findById(id);

		return mapper.toDto(rental, calculateLateFee(rental));
	}

	@Override
	public RentalDto rentVhs(RentalCreateDto dto) {
		Long vhsId = dto.getVhsId();
		if (repository.existsByVhsIdAndReturnDateIsNull(vhsId))
			throw new VhsUnavailableException();

		Vhs vhs = vhsService.findById(vhsId);
		User user = userService.findById(dto.getUserId());
		LocalDate rentalDate = LocalDate.now();
		LocalDate dueDate = calculateDueDate(rentalDate);

		Rental rental = new Rental(vhs, user, rentalDate, dueDate);
		Rental createdRental = repository.save(rental);

		return mapper.toDto(createdRental, BigDecimal.ZERO);
	}

	private LocalDate calculateDueDate(LocalDate rentalDate) {
		return rentalDate.plusMonths(1);
	}

	@Override
	public RentalDto returnVhs(Long id, RentalReturnDto dto) {
		Rental rental = findById(id);

		rental.setReturnDate(dto.getReturnDate());

		Rental updatedRental = repository.save(rental);
		BigDecimal lateFee = calculateLateFee(updatedRental);

		return mapper.toDto(updatedRental, lateFee);
	}

	// Late fee = 0.1 per day
	private BigDecimal calculateLateFee(Rental rental) {
		LocalDate returnDate = Optional.ofNullable(rental.getReturnDate()).orElse(LocalDate.now());
		LocalDate dueDate = rental.getDueDate();

		if (returnDate.isAfter(dueDate)) {
			long daysLate = dueDate.until(returnDate, ChronoUnit.DAYS);
			return BigDecimal.valueOf(daysLate * 0.1).setScale(2, RoundingMode.HALF_EVEN);
		} else {
			return BigDecimal.ZERO;
		}
	}

	@Override
	public RentalDto updateRental(Long id, RentalDto dto) {
		Rental rental = findById(id);
		User user = userService.findById(dto.getUser().getId());
		Vhs vhs = vhsService.findById(dto.getVhs().getId());

		rental.setVhs(vhs);
		rental.setUser(user);
		rental.setDueDate(dto.getDueDate());
		rental.setRentalDate(dto.getRentalDate());
		rental.setReturnDate(rental.getReturnDate());

		Rental updatedRental = repository.save(rental);
		BigDecimal lateFee = calculateLateFee(updatedRental);

		return mapper.toDto(updatedRental, lateFee);
	}

	@Override
	public void deleteRental(Long id) {
		Rental rental = findById(id);
		repository.delete(rental);
	}

	@Override
	public Rental findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new NoResourceException("rental.notfound"));
	}

}
