package it.unicredit.demo.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.unicredit.demo.Person;
import it.unicredit.demo.repository.PersonRepository;
import it.unicredit.demo.services.PersonService;
import it.unicredit.demo.utils.DatabaseException;
import it.unicredit.demo.utils.PersonNotFoundException;

@Service
public class PersonServiceImpl implements PersonService {
	
	private PersonRepository personRepository;
	
	public PersonServiceImpl(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

    @Override
    public List<Person> retrieveAll() {
        try {
            return personRepository.findAll();
        } catch (Exception e) {
            throw new DatabaseException("Error retrieving all persons from the database.");
        }
    }

    @Override
    public Person updatePerson(String phone_number, Person p) {
        Optional<Person> existingPersonOptional = personRepository.findById(phone_number);
        if (!existingPersonOptional.isPresent()) {
            throw new PersonNotFoundException(p.getPhoneNumber());
        }
        
        Person existingPerson = existingPersonOptional.get();
        existingPerson.setFirstName(p.getFirstName());
        existingPerson.setLastName(p.getLastName());
        existingPerson.setPhoneNumber(p.getPhoneNumber());
      //  existingPerson.setLastModified(new Date());
        
        return personRepository.save(existingPerson);
    }



    @Override
    public Person addPerson(Person p) {
        if (p.getFirstName() == null || p.getLastName() == null) {
            throw new DatabaseException("Error adding person!\nFirst name and last name cannot be null.");
        }

       // p.setLastModified(new Date());
        return personRepository.save(p);
    }

    @Override
    public void deletePersonByPhoneNumber(String phoneNumber) {
        try {
            personRepository.deleteById(phoneNumber);
        } catch (Exception e) {
            throw new DatabaseException("Error deleting person with " + phoneNumber + " phone number.");
        }
    }

    @Override
    public List<Person> searchPersonByLastName(String lastName) {
        try {
            return personRepository.findByLastName(lastName);
        } catch (Exception e) {
            throw new DatabaseException("Error searching persons with '" + lastName + "' surname from the database.");
        }
    }

    @Override
    public Person searchPersonByPhoneNumber(String phoneNumber) {
        Person person = personRepository.findById(phoneNumber).orElse(null);
        if (person == null) {
            throw new PersonNotFoundException(phoneNumber);
        }
        return person;
    }

}
