/**
 * Contact.java
 * Created on 14 Feb. 2016
 * Package: net.sf.memoranda
 * 
 * Contact Object implementation class.
 * @author Moretti
 *
 */
package net.sf.memoranda;

public class Contact {
    
    private String _name = null;	// the name of the contact
    private String _email = null;	// the e-mail of the contact
    private String _phone = null;	// the phone number of the contact
    private String _notes = null;	// the notes of the contact
    
    /**
     * Constructor for Contact
     * @param name The name of the contact
     * @param email The e-mail address of the contact
     * @param phone The phone number of the contact
     * @param notes Notes about the contact
     */
    public Contact(String name, String email, String phone, String notes) {
        _name = name;
        _email = email;
        _phone = phone;
        _notes = notes;
    }
    
    /**
     * Empty Constructor for Contact
     */
    public Contact() {
        _name = null;
        _email = null;
        _phone = null;
        _notes = null;
    }
    
    /**
     * Getter for name
     * @returns The name of contact
     */
    public String getName() {
        return _name;
    }
    
    /**
     * Getter for email
     * @returns The e-mail address of contact
     */
    public String getEmail() {
        return _email;
    }
    
    /**
     * Getter for phone
     * @returns The phone number of contact
     */
    public String getPhone() {
        return _phone;
    }
    
    /**
     * Getter for notes
     * @returns The notes about the contact
     */
    public String getNotes() {
        return _notes;
    }

}
