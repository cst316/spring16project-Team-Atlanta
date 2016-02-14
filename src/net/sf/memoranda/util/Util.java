/**
 * Util.java
 * Created on 11.02.2003, 23:59:21 Alex
 * Package: net.sf.memoranda.util
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda team: http://memoranda.sf.net
 */
package net.sf.memoranda.util;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import javax.swing.JFileChooser;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.ui.App;
import net.sf.memoranda.ui.AppFrame;
import net.sf.memoranda.ui.ExceptionDialog;
import java.util.Random;

/**
 * Description of Util.java Util.java provides many utilities to the memoranda
 * application. These utilities provide functionality that is needed for the
 * application to access resources such as the calendar, and web browser.
 * 
 * @author alexeya
 * @author Meyung
 * @version 1.14 Feb 8, 2016
 */
public class Util {

	static long seed = 0;

	public static String generateId() {
		long seed1 = System.currentTimeMillis();
		while (seed1 == seed)
			seed1 = System.currentTimeMillis(); // Make sure we'll don't get the
												// same seed twice
		seed = seed1;
		Random r = new Random(seed);
		return Integer.toString(r.nextInt(), 16) + "-" + Integer.toString(r.nextInt(65535), 16) + "-"
				+ Integer.toString(r.nextInt(65535), 16) + "-" + Integer.toString(r.nextInt(65535), 16);

	}

	public static String getDateStamp(Calendar cal) {
		return cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH)) + "/"
				+ new Integer(cal.get(Calendar.YEAR)).toString();

	}

	public static String getDateStamp(CalendarDate date) {
		return Util.getDateStamp(date.getCalendar());
	}

	public static int[] parseDateStamp(String s) {
		s = s.trim();
		int i1 = s.indexOf("/");
		int i2 = s.indexOf("/", i1 + 1);
		int[] date = new int[3];
		date[0] = new Integer(s.substring(0, i1)).intValue();
		date[1] = new Integer(s.substring(i1 + 1, i2)).intValue();
		date[2] = new Integer(s.substring(i2 + 1)).intValue();
		return date;
		/*
		 * DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT,
		 * currentLocale); Date d = null; try { d = dateFormat.parse(s); } catch
		 * (Exception ex) { ex.printStackTrace(); return new int[3]; } int[] ret
		 * = {d.getDay(), d.getMonth(), d.getYear()}; return ret;
		 */
	}

	public static String getEnvDir() {
		// Changed static building of getEnvDir
		// Now system-related path-separator is used
		String p = System.getProperty("user.home") + File.separator + ".jnotes2" + File.separator;
		if (new File(p).isDirectory())
			return p;
		return System.getProperty("user.home") + File.separator + ".memoranda" + File.separator;
	}

	public static String getCDATA(String s) {
		return "<![CDATA[" + s + "]]>";
	}

	/**
	 * Description of getDefaultDesktop() The getDefaultDesktop() checks to
	 * ensure that the java.awt.Destop class is supported. If so, this means
	 * that it is possible to use Desktop.getDesktop() to create an instance of
	 * a Desktop object.
	 * 
	 * @param none
	 * @return Desktop
	 */
	public static Desktop getDefaultDesktop() {
		Desktop desktop;
		if (java.awt.Desktop.isDesktopSupported()) {
			desktop = java.awt.Desktop.getDesktop();
		} else {
			return null;
		}
		return desktop;
	}

	/**
	 * Description of convertURLtoURI(String url) The convertURLtoURI() checks
	 * to ensure that the given String can be converted into a URI. If it can it
	 * will return the URI object.
	 * 
	 * @param url
	 * @return URI
	 */
	public static URI convertURLtoURI(String url) {
		URI uri;
		url = url.replace('\\', '/');
		try {
			uri = java.net.URI.create(url);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		if (uri.toString().isEmpty()) {
			return null;
		}
		return uri;
	}

	/**
	 * Description of runBrowser(String url) The runBrowser() first looks to
	 * automatically detect a default browser that can be used to open the
	 * provided url. If it is able to it will open the url in a default web
	 * browser. If this method is not able to open the provided url in a default
	 * web browser, then it will ask the user to locate a browser executable. If
	 * the user does so successfully then this method will open the provided url
	 * in the specified browser.
	 * 
	 * @param url
	 * @return Boolean True if a Browser was located. False if no browser could
	 *         be located.
	 */
	public static boolean runBrowser(String url) {

		Desktop desktop;
		URI uri;

		if (getDefaultDesktop() != null) {
			if (convertURLtoURI(url) != null) {
				desktop = getDefaultDesktop();
				uri = convertURLtoURI(url);
				try {
					desktop.browse(uri);
					return true;
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}

		if (!checkBrowser())
			return false;
		String commandLine = MimeTypesList.getAppList().getBrowserExec() + " " + url;
		System.out.println("Run: " + commandLine);
		try {
			/* DEBUG */
			Runtime.getRuntime().exec(commandLine);
		} catch (Exception ex) {
			new ExceptionDialog(ex,
					"Failed to run an external web-browser application with commandline<br><code>" + commandLine
							+ "</code>",
					"Check the application path and command line parameters "
							+ "(File-&gt;Preferences-&gt;Resource types).");
		}
		return true;
	}

	public static boolean checkBrowser() {
		AppList appList = MimeTypesList.getAppList();
		String bpath = appList.getBrowserExec();
		if (bpath != null)
			if (new File(bpath).isFile())
				return true;
		JFileChooser chooser = new JFileChooser();
		chooser.setFileHidingEnabled(false);
		chooser.setDialogTitle(Local.getString("Select the web-browser executable"));
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		/*
		 * java.io.File lastSel = (java.io.File)
		 * Context.get("LAST_SELECTED_RESOURCE_FILE"); if (lastSel != null)
		 * chooser.setCurrentDirectory(lastSel);
		 */
		if (chooser.showOpenDialog(App.getFrame()) != JFileChooser.APPROVE_OPTION)
			return false;
		appList.setBrowserExec(chooser.getSelectedFile().getPath());
		CurrentStorage.get().storeMimeTypesList();
		return true;
	}

	public static String getHoursFromMillis(long ms) {
		double numSeconds = (((double) ms) / 1000d);
		return String.valueOf(numSeconds / 3600);
	}

	public static long getMillisFromHours(String hours) {
		try {
			double numHours = Double.parseDouble(hours);
			double millisDouble = (numHours * 3600 * 1000);
			return (long) millisDouble;
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	static Set tempFiles = new HashSet();

	static {
		AppFrame.addExitListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				for (Iterator i = tempFiles.iterator(); i.hasNext();)
					((File) i.next()).delete();
			}
		});
	}

	public static File getTempFile() throws IOException {
		File f = File.createTempFile("tmp", ".html", null);
		tempFiles.add(f);
		return f;
	}

	public static void debug(String str) {
		System.out.println("[DEBUG] " + str);
	}

	/**
	 * @param e
	 */
	public static void error(Exception e) {
		System.out.println("[ERROR] Exception: " + e.getClass().getName());
		System.out.println("[ERROR] Exception Message: " + e.getMessage());

		String stackTrace = "";
		StackTraceElement[] ste = e.getStackTrace();
		for (int i = 0; i < ste.length; i++) {
			stackTrace = ste[i].toString() + "\n";
		}
		System.out.println("[ERROR] Stack Trace: " + stackTrace);
	}
}
