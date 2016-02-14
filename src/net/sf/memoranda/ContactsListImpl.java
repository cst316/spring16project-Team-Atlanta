/**
 * ContactsListImpl.java
 * Created on 14 Feb. 2016
 * Package: net.sf.memoranda
 * 
 * Contacts List Implementation Class.
 * @author Moretti
 */
package net.sf.memoranda;

import java.util.Vector;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

public class ContactsListImpl implements ContactsList {
    
	private Project _project = null;
    private Document _doc = null;
    private Element _root = null;

    /**
     * Constructor for ContactsListImpl.
     */
    public ContactsListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
    }

    public ContactsListImpl(Project prj) {
            _root = new Element("contacts-list");
            _doc = new Document(_root);
            _project = prj;
    }

    /**
     * Method to return a vector of all contacts in the project.
     * @see net.sf.memoranda.ContactsList#getAllContacts()
     * @returns Vector of all contacts in the project
     */
    public Vector getAllContacts() {
        Vector v = new Vector();
        Elements cs = _root.getChildElements("contact");
        for (int i = 0; i < cs.size(); i++) {
        	v.add(new Contact(cs.get(i).getAttribute("name").getValue(),
            		cs.get(i).getAttribute("email").getValue(), cs.get(i).getAttribute("phone").getValue(),
            		cs.get(i).getAttribute("notes").getValue()));
        }
        return v;
    }

    /**
     * Method to get contact that matches attribute "name."
     * @see net.sf.memoranda.ContactsList#getContact(java.lang.String)
     * @returns Contact within vector that matches attribute "name"
     */
    public Contact getContact(String name) {
        Elements cs = _root.getChildElements("contact");
        for (int i = 0; i < cs.size(); i++)
            if (cs.get(i).getAttribute("name").getValue().equals(name))
                return new Contact(cs.get(i).getAttribute("name").getValue(),
                		cs.get(i).getAttribute("email").getValue(), cs.get(i).getAttribute("phone").getValue(),
                		cs.get(i).getAttribute("notes").getValue());
        return null;
    }

    /**
     * Method to add contact to Element.
     * @see net.sf.memoranda.ContactsList#addContact(java.lang.String)
     */
    public void addContact(String name, String email, String phone, String notes) {
        Element el = new Element("contact");
        el.addAttribute(new Attribute("id", Util.generateId()));
        el.addAttribute(new Attribute("name", name));  
        el.addAttribute(new Attribute("email", email));
        el.addAttribute(new Attribute("phone", phone));
        el.addAttribute(new Attribute("notes", notes));
        _root.appendChild(el);
    }

    public void addContact(String name) {
        addContact(name, "", "", "");
    }

    /**
     * Method to remove contact that matches attribute "name."
     * @see net.sf.memoranda.ContactsList#removeContact(java.lang.String)
     */
    public void removeContact(String name) {
        Elements cs = _root.getChildElements("contact");
        for (int i = 0; i < cs.size(); i++)
            if (cs.get(i).getAttribute("name").getValue().equals(name)) {
            	_root.removeChild(cs.get(i));
            }
    }

    /**
     * Method returns number value of the number of contacts in the project.
     * @see net.sf.memoranda.ContactsList#getAllContactsCount()
     * @returns Integer value of the number of contacts within the project
     */
    public int getAllContactsCount() {
        return _root.getChildElements("contact").size();
    }
    
    /**
     * @see net.sf.memoranda.ContactsList#getXMLContent()
     */
    public Document getXMLContent() {
        return _doc;
    }
}
