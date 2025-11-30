package tn.vhs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.vhs.model.Vhs;

@Repository
public interface VhsRepository extends JpaRepository<Vhs, Long> {

	@Query("SELECT DISTINCT v.genre FROM Vhs v ORDER BY v.genre ASC")
	List<String> findDistinctGenres();

	@Query("SELECT DISTINCT v.releaseYear FROM Vhs v ORDER BY v.releaseYear ASC")
	List<Integer> findDistinctReleaseYears();

	@Query("""
			SELECT v FROM Vhs v
			WHERE (:genre IS NULL OR v.genre = :genre)
			  AND (:releaseYear IS NULL OR v.releaseYear = :releaseYear)
			""")
	List<Vhs> findByGenreAndReleaseYear(@Param("genre") String genre, @Param("releaseYear") Integer releaseYear);

}
