package it.unicredit.demo.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.unicredit.demo.Person;
import it.unicredit.demo.services.PersonService;
import it.unicredit.demo.utils.DatabaseException;
import it.unicredit.demo.utils.PersonNotFoundException;

@RestController
@RequestMapping("/contacts")
public class PersonController {

    PersonService addrBookService;
    
    public PersonController(PersonService addrBookService) {
		super();
		this.addrBookService = addrBookService;
	}
    
    /*
     * GET
     */
    @GetMapping("/retrieveAll")
    public ResponseEntity<?> retrieveContacts() {
        List<Person> contacts = addrBookService.retrieveAll();

        if (contacts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nobody found in the address book.");
        }

        return ResponseEntity.ok(contacts);
    }
    @GetMapping("/searchByLastName/{last_name}")
    public ResponseEntity<List<Person>> searchByLastName(@PathVariable String last_name) {
        try {
            List<Person> persons = addrBookService.searchPersonByLastName(last_name);
            if (persons.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok(persons);
            }
        } catch (DatabaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/searchByPhoneNumber/{phone_number}")
    public ResponseEntity<Person> searchByPhoneNumber(@PathVariable String phone_number) {
        try {
            Person person = addrBookService.searchPersonByPhoneNumber(phone_number);
            return ResponseEntity.ok(person);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (DatabaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
  
    /*
     * POST
     */
    @PostMapping("/add")
    public ResponseEntity<String> addContact(@RequestBody Person p) {
    	
    	if (p.getFirstName() == null || p.getLastName() == null) {
            throw new IllegalArgumentException("First name and last name cannot be null.");
        }
    	
        try {
            Person addedPerson = addrBookService.addPerson(p);
            if (addedPerson != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Contact added: " + addedPerson.getFirstName() + " " + addedPerson.getLastName());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding the contact.");
            }
        } catch (DatabaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding the contact: " + e.getMessage());
        }
    }

    /*
     * PUT
     */
    @PutMapping("/update/{phone_number}")
    public ResponseEntity<String> updateContact(@PathVariable String phone_number, @RequestBody Person newPerson) {
            
    	Person updatedPerson = addrBookService.updatePerson(phone_number, newPerson);

            if (updatedPerson != null) {
                return ResponseEntity.ok("Contact updated: " + updatedPerson.getFirstName() + " " + updatedPerson.getLastName());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating the contact.");
            }
    }


    /*
     * DELETE
     */
    @DeleteMapping("/delete/{phone_number}")
    public ResponseEntity<String> deleteContact(@PathVariable String phone_number) {
        try {
            addrBookService.deletePersonByPhoneNumber(phone_number);
            return ResponseEntity.ok("Contact with phone number " + phone_number + " deleted.");
        } catch (DatabaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting contact: " + e.getMessage());
        } catch (PersonNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact not found: " + e.getMessage());
        }
    }

}
