package tn.vhs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.vhs.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
	
	boolean existsByVhsIdAndReturnDateIsNull(Long vhsId);

}
