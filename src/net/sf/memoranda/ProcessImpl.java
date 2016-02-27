/**
 * ProcessImpl.java
 * Created on 23 Feb. 2016
 * Package: net.sf.memoranda
 * 
 * Process Implementation  class.
 * @author Meyung
 *
 */

package net.sf.memoranda;

public class ProcessImpl implements Process {

	private String _processName = null;		// the name of the process
	private String _scrumMaster = null;		// the Scrum Master for the process
	private String _productOwner = null; 	// the Product Owner for the process
	private String _teamMembers = null; 	// the Team Members in the process
	private String _sprintPlanningMeeting = null;	// the Sprint Planning Meeting for the process
	private String _dailyStandUp = null; 	// the Daily Stand-Up for the process
	private String _sprintReview = null;	// the Sprint Review for the process
	private String _sprintRetrospective = null;	 // the Sprint Retrospective for the process

	/**
	 * Constructor for ProcessImpl
	 * @param processName The name of the process
	 * @param scrumMaster The Scrum Master for the process
	 * @param productOwner The Product Owner for the process
	 * @param teamMembers The Team Members in the process
	 * @param sprintPlanningMeeting The Sprint Planning Meeting for the process
	 * @param dailyStandUp The Daily Stand-Up for the process
	 * @param sprintReview The Sprint Review for the process
	 * @param sprintRetrospective The Sprint Retrospective for the process
	 */
	public ProcessImpl(String processName, String scrumMaster, String productOwner, String teamMembers, String sprintPlanningMeeting, String dailyStandUp, String sprintReview, String sprintRetrospective) {
		_processName = processName;
		_scrumMaster = scrumMaster;
		_productOwner = productOwner;
		_teamMembers = teamMembers;
		_sprintPlanningMeeting = sprintPlanningMeeting;
		_dailyStandUp = dailyStandUp;
		_sprintReview = sprintReview;
		_sprintRetrospective = sprintRetrospective;
	}

	/**
	 * Empty Constructor for ProcessImpl
	 */
	public ProcessImpl() {
		_processName = null;
		_scrumMaster = null;
		_productOwner = null;
		_teamMembers = null;
		_sprintPlanningMeeting = null;
		_dailyStandUp = null;
		_sprintReview = null;
		_sprintRetrospective = null;
	}

	/**
	 * Getter for processName
	 * @returns The name of the process
	 */
	public String getProcessName() {
		return _processName;
	}

	/**
	 * Getter for scrumMaster
	 * @returns The Scrum Master for the process
	 */
	public String getScrumMaster() {
		return _scrumMaster;
	}

	/**
	 * Getter for productOwner
	 * @returns The Product Owner for the process
	 */
	public String getProductOwner() {
		return _productOwner;
	}

	/**
	 * Getter for teamMembers
	 * @returns The Team Members in the process
	 */
	public String getTeamMembers() {
		return _teamMembers;
	}

	/**
	 * Getter for sprintPlanningMeeting
	 * @returns The Sprint Planning Meeting for the process
	 */
	public String getSprintPlanningMeeting() {
		return _sprintPlanningMeeting;
	}

	/**
	 * Getter for dailyStandUp
	 * @returns The Daily Stand-Up for the process
	 */
	public String getDailyStandUp() {
		return _dailyStandUp;
	}

	/**
	 * Getter for sprintReview
	 * @returns The Sprint Review for the process
	 */
	public String getSprintReview() {
		return _sprintReview;
	}

	/**
	 * Getter for sprintRetrospective
	 * @returns The Sprint Retrospective for the process
	 */
	public String getSprintRetrospective() {
		return _sprintRetrospective;
	}

}
