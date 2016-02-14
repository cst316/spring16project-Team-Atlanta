/**
 * ContactsList.java
 * Created on 14 Feb. 2016
 * Package: net.sf.memoranda
 * 
 * Contacts List interface.
 * @author Moretti
 */
package net.sf.memoranda;

import java.util.Vector;
import nu.xom.Document;

public interface ContactsList {
    
    Vector getAllContacts();
    
    Contact getContact(String name);
    
    void addContact(String name, String email, String phone, String notes);
    
    void removeContact(String name);
        
    int getAllContactsCount();
    
    Document getXMLContent();

}
