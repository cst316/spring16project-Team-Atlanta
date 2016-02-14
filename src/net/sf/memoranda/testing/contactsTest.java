/**
 * 
 */
package net.sf.memoranda.testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.sf.memoranda.Contact;
import net.sf.memoranda.ContactsList;
import net.sf.memoranda.ContactsListImpl;
import net.sf.memoranda.Project;
import nu.xom.Document;
import nu.xom.Element;

/**
 * JUnit Test Class for Contacts implementation.
 * @author Moretti
 *
 */
public class contactsTest {
	private Contact con1 = null;
	private Project _project = null;
	private ContactsList conList1 = null;
	
	@Before
	public void setUp() throws Exception {
		con1 = new Contact("Chris","chris@hotmail.com","123-456-7890","Notes about Chris");
		conList1 = new ContactsListImpl(_project);
		conList1.addContact("Frank", "frank@gmail.com", "109-876-5432", "Frank's Notes");
	}

	/**
	 * Test method for {@link net.sf.memoranda.Contact#Contact()}
	 * This test method ensures that the Contact object is properly
	 * instantiated.
	 */
	@Test
	public final void testContactObject() {
		assertNotNull(con1);
	}

	/**
	 * Test method for {@link net.sf.memoranda.Contact#getName()},
	 * {@link net.sf.memoranda.Contact#getEmail()}, {@link net.sf.memoranda.Contact#getPhone()},
	 * and {@link net.sf.memoranda.Contact#getNotes()}
	 * This test method ensures that the Contact object getters are returning
	 * the proper values based on the instantiated object.
	 */
	@Test
	public final void testContactElementAttributes() {
		assertEquals(con1.getName(),"Chris");
		assertEquals(con1.getEmail(),"chris@hotmail.com");
		assertEquals(con1.getPhone(),"123-456-7890");
		assertEquals(con1.getNotes(),"Notes about Chris");
	}
	
	/**
	 * Test method for {@link net.sf.memoranda.ContactsListImpl#ContactList()}
	 * This test method ensures that the ContactList element is properly
	 * instantiated.
	 */
	@Test
	public final void testContactListObject() {
		assertNotNull(conList1.getContact("Frank"));
		assertTrue(conList1.getAllContactsCount() == 1);
	}
	
	/**
	 * Test method for {@link net.sf.memoranda.ContactsListImpl#removeContact()}
	 * This test method ensures that the ContactList element is properly
	 * removed and all appropriate methods are updated accordingly.
	 */
	@Test
	public final void testRemoveContactFromList() {
		conList1.removeContact("Frank");
		assertTrue(conList1.getAllContactsCount() == 0);
	}
}
