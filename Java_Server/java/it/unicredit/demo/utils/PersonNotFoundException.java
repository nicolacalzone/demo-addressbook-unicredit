package it.unicredit.demo.utils;

public class PersonNotFoundException extends RuntimeException {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PersonNotFoundException(String phoneNumber) {
        super("Person with phone number " + phoneNumber + " not found.");
    }
}