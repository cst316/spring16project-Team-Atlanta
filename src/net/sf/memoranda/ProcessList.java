/**
 * TaskList.java
 * Created on 21.02.2003, 12:25:16 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;
import java.util.Collection;

import net.sf.memoranda.date.CalendarDate;
/**
 * 
 */
public interface ProcessList {

	Project getProject();
    Process getProcess(String id);

    Process createProcess(CalendarDate startDate, CalendarDate endDate, String text, int priority, long effort, String description, String parentProcessId);
	
    nu.xom.Document getXMLContent();

}
