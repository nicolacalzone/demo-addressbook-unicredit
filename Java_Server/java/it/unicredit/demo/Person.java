package it.unicredit.demo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="person")
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    private String phone_number;
    private String first_name;
    private String last_name;
    //private Date last_modified;

    public Person() {
    }
    
    public Person(String phone_number, String first_name, String last_name) {
		super();
		this.phone_number = phone_number;
		this.first_name = first_name;
		this.last_name = last_name;
	}
    
//  public Person(String phone_number, String first_name, String last_name, Date last_modified) {
//		super();
//		this.phone_number = phone_number;
//		this.first_name = first_name;
//		this.last_name = last_name;
//		this.last_modified = last_modified;	
//	}
//	protected void onCreate() {
//        last_modified = new Date();
//    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

//    public Date getLastModified() {
//        return last_modified;
//    }
//
//    public void setLastModified(Date last_modified) {
//        this.last_modified = last_modified;
//    }
}
