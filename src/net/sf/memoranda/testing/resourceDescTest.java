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
import net.sf.memoranda.Resource;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.ResourcesListImpl;

/**
 * JUnit Test Class for Resource Description implementation.
 * @author Moretti
 *
 */
public class resourceDescTest {
	private Resource res1 = null;
	private Resource res2 = null;
	private Project _project = null;
	private ResourcesList resList1 = null;
	
	@Before
	public void setUp() throws Exception {
		res1 = new Resource("www.google.com","Google Homepage", true, false);
		res2 = new Resource("C:/Test.html","Test HTML File", false, false);
		resList1 = new ResourcesListImpl(_project);
		resList1.addResource("www.yahoo.com","Yahoo Homepage", true, false);
		resList1.addResource("C:/Test2.txt","Test TXT File", false, false);
	}

	/**
	 * Test method for {@link net.sf.memoranda.Resource#Resource()}
	 * This test method ensures that the Resource object is properly
	 * instantiated with new parameter.
	 */
	@Test
	public final void testResourceObject() {
		assertNotNull(res1);
		assertNotNull(res2);
	}

	/**
	 * Test method for {@link net.sf.memoranda.Resource#getPath()},
	 * {@link net.sf.memoranda.Resource#getDesc()}, {@link net.sf.memoranda.Resource#isNetShortcut()},
	 * and {@link net.sf.memoranda.Resource#isProjectFile()}
	 * This test method ensures that the Contact object getters are returning
	 * the proper values based on the instantiated object.
	 */
	@Test
	public final void testResourceElementAttributes() {
		assertEquals(res1.getPath(),"www.google.com");
		assertEquals(res1.getDesc(),"Google Homepage");
		assertTrue(res1.isInetShortcut());
		assertTrue(!res1.isProjectFile());
	}
	
	/**
	 * Test method for {@link net.sf.memoranda.ResourcesListImpl#ResourceList()}
	 * This test method ensures that the ResourceList element is properly
	 * instantiated with new attribute.
	 */
	@Test
	public final void testContactListObject() {
		assertNotNull(resList1.getResource("www.yahoo.com"));
		assertTrue(resList1.getAllResourcesCount() == 2);
	}
	
	/**
	 * Test method for {@link net.sf.memoranda.ResourcesListImpl#removeResource()}
	 * This test method ensures that the ResourceList element is properly
	 * removed and all appropriate methods are updated accordingly.
	 */
	@Test
	public final void testRemoveContactFromList() {
		resList1.removeResource("www.yahoo.com");
		assertTrue(resList1.getAllResourcesCount() == 1);
	}
}
