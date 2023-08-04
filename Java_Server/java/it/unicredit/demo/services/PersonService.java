package it.unicredit.demo.services;

import java.util.List;

import it.unicredit.demo.Person;

public interface PersonService {
	
	/*
	 * POST and PUT
	 */
    public Person updatePerson(String phone_number, Person p);
    public Person addPerson(Person p);
    
    /*
     * DELETE
     */
    public void deletePersonByPhoneNumber(String phoneNumber);
    
    /*
     * GET
     */
    public List<Person> retrieveAll();
    public List<Person> searchPersonByLastName(String lastName);
    public Person searchPersonByPhoneNumber(String phoneNumber);
}
