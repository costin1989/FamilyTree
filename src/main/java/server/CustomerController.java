package server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import server.jpa.Person;

@RestController
@RequestMapping("/person")
public class CustomerController {
	@Autowired
	CustomerRepository repository;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addPerson(@RequestBody Person person) {
		repository.save(new Person(person.getFirstName(), person.getLastName()));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public void updatePerson(@RequestBody Person person) {
		repository.save(new Person(person.getId(),person.getFirstName(), person.getLastName()));
	}
	
	@RequestMapping("remove/{id}")
	public void deletePerson(@PathVariable("id") long id) {
		Person person = repository.findOne(id);
		if(person == null) {
			throw new IllegalArgumentException("Person with id"+id+" doesn't exists!");
		}
		repository.delete(person);
	}
	
	@RequestMapping("delete/{id}")
	public void softDeletePerson(@PathVariable("id") long id) {
		Person person = repository.findOne(id);
		if(person == null) {
			throw new IllegalArgumentException("Person with id"+id+" doesn't exists!");
		}
		person.setDeleted(true);
		repository.save(person);
	}

	@RequestMapping("/findall")
	public Response findAll() {

		Iterable<Person> customers = repository.findAll();

		return new Response("Done", customers);
	}

	@RequestMapping("/{id}")
	public Response findCustomerById(@PathVariable("id") long id) {

		Person customer = repository.findOne(id);

		return new Response("Done", customer);
	}

	@RequestMapping("/findbylastname")
	public Response findByLastName(@RequestParam("lastName") String lastName) {

		List<Person> customers = repository.findByLastName(lastName);

		return new Response("Done", customers);
	}
	
	@RequestMapping("/findbyfirstname")
	public Response findByFirstName(@RequestParam("firstName") String firstName) {

		List<Person> customers = repository.findByFirstName(firstName);

		return new Response("Done", customers);
	}
	
	@RequestMapping("/findbydeleted")
	public Response findDeleted() {

		List<Person> persons = repository.findByDeleted(true);

		return new Response("Done", persons);
	}
}