/**
 * Process.java
 * Created on 23 Feb. 2016
 * Package: net.sf.memoranda
 * 
 * Process Interface.
 * @author Meyung
 *
 */
package net.sf.memoranda;

public interface Process {

	public String getProcessName();    

	public String getScrumMaster();

	public String getProductOwner();

	public String getTeamMembers();

	public String getSprintPlanningMeeting();

	public String getDailyStandUp();

	public String getSprintReview();

	public String getSprintRetrospective();

}
