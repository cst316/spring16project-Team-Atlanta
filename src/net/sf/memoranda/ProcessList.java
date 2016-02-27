/**
 * ProcessList.java
 * Created on 23 Feb. 2016
 * Package: net.sf.memoranda
 * 
 * Process List interface.
 * @author Meyung
 */

package net.sf.memoranda;

import java.util.Vector;
import nu.xom.Document;

public interface ProcessList {

	Vector<ProcessImpl> getAllProcesses();

	Process getProcess(String processName);

	void addProcess(String processName, String scrumMaster, String productOwner, String teamMembers, String sprintPlanningMeeting, String dailyStandUp, String sprintReview, String sprintRetrospective);

	void removeProcess(String processName);

	int getAllProcessCount();

	Document getXMLContent();

}
