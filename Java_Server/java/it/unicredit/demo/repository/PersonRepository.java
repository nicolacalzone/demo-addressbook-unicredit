package it.unicredit.demo.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.unicredit.demo.Person;

public interface PersonRepository extends JpaRepository<Person, String> {
	
	@Query("SELECT p FROM Person p WHERE p.last_name = :last_name")
    List<Person> findByLastName(@Param("last_name") String last_name);
	
	@Cacheable("all_persons")
	List<Person> findAll();
}
