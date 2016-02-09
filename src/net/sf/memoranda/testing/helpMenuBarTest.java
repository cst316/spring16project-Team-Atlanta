/**
 * 
 */
package net.sf.memoranda.testing;

import static org.junit.Assert.*;

import java.net.URI;

import org.junit.Test;

import net.sf.memoranda.util.Util;

/**
 * @author Meyung
 *
 */
public class helpMenuBarTest {

	/**
	 * Test method for {@link net.sf.memoranda.util.Util#getDefaultDesktop()}
	 * This test method ensures that the getDefaultDesktop() is returning a
	 * Desktop object.
	 */
	@Test
	public final void testGetDefaultDesktop() {
		assertTrue(Util.getDefaultDesktop().equals(java.awt.Desktop.getDesktop()));
	}

	/**
	 * Test method for
	 * {@link net.sf.memoranda.util.Util#convertURLtoURI(java.lang.String)} This
	 * test method ensures that the convertURLtoURI() returns a URI when a
	 * String is supplied as the argument.
	 */
	@Test
	public final void testConvertURLtoURI() {
		String url = "http://memoranda.sourceforge.net";
		String urlEmpty = "";
		assertTrue(Util.convertURLtoURI(url) instanceof URI);
		assertNull(Util.convertURLtoURI(urlEmpty));
	}

	/**
	 * Test method for
	 * {@link net.sf.memoranda.util.Util#runBrowser(java.lang.String)} This test
	 * method ensures that the runBrowser() returns a True when there is a
	 * default Web browser and the String supplied can be converted to a URI.
	 */
	@Test
	public final void testRunBrowser() {
		String url = "http://memoranda.sourceforge.net";
		if (java.awt.Desktop.isDesktopSupported()) {
			if (Util.convertURLtoURI(url) != null) {
				assertTrue(Util.runBrowser(url));
			}
		}
	}

}
