package server;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import server.jpa.Person;

public interface CustomerRepository extends CrudRepository<Person, Long> {
	List<Person> findByLastName(String lastName);

	List<Person> findByFirstName(String firstName);

	List<Person> findByDeleted(boolean deleted);
}