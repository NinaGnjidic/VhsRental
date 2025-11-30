package tn.vhs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.vhs.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
