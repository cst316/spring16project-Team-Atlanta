package net.sf.memoranda.util;

import net.sf.memoranda.*;
import net.sf.memoranda.date.CurrentDate;
import java.util.*;

/**
 * 
 * @author BrandonGrow
 *
 */
public class ReportExporter {

	private String projectTitle;
	private String projectStartDate;
	private String projectEndDate; 
	private String projectStatus;
	private TaskList taskList;
	private NoteList noteList;
	
	
	/**
	 * 
	 * @param taskList
	 * @param noteList
	 */
	public ReportExporter(TaskList taskList, NoteList noteList) {
		this.projectTitle = taskList.getProject().getTitle();
		this.projectStartDate = taskList.getProject().getStartDate().toString();
		if(taskList.getProject().getEndDate() == null){
			this.projectEndDate = "None";
		} else {
			this.projectEndDate = taskList.getProject().getEndDate().toString();
		}
		
		this.taskList = taskList; 
		this.noteList = noteList; 
		
		}	
	
	
	/**
	 * 
	 * @return
	 */
	public String toHTML() {
		
		String writeToDoc = "<!DOCTYPE html><html>";
		String notesLabel = "Notes";
		String tasksLabel = "Tasks";
		String statusLabel = "Status not available. Set end date for project to view status!";
		

		writeToDoc += "<head><title>Project Report</title></head>";
		writeToDoc += "<body>";
		writeToDoc += "<h2>"+projectTitle+"</h2>";
		
		/**
		 * Shows the projects start and end data. If there is no end date then only 
		 * the start date is shown. Both are shown if there is an end date.
		 */
		if(projectEndDate == "None") {
			writeToDoc += "<small>Project Start Date: " +projectStartDate+ "</small>";
		} else {
			writeToDoc += "<small>Project Duration: "+projectStartDate+" - " +projectEndDate+ "</small>";
		}
		
		writeToDoc += "<br>";
		
		//status is shown here. If not status available, then a message occurs to the user.
		if(projectStatus == null) {
			writeToDoc += "<small>Status: "+statusLabel+"</small>";
		} else {
			writeToDoc += "<small>Status: "+projectStatus+"</small>";
		}
		writeToDoc += "<br>";
		
		
		//Prints out the notes information to the report
		writeToDoc += "\n<hr></hr><a" +"</title>\n</head>\n<body>\n<h2>"+notesLabel +"\n<br>\n" + "</h2>\n"; 
		for (Iterator iterator = noteList.getAllNotes().iterator(); iterator.hasNext();) {
			writeToDoc = noteGenerator(writeToDoc, (Note)iterator.next());    
			writeToDoc += "</n>";
		}
		writeToDoc += "<br>";
		
		
		//Prints out the task information to the report
		writeToDoc += "\n<hr></hr><a" +"</title>\n</head>\n<body>\n<h2>"+tasksLabel +"\n<br>\n" + "</h2>\n"; 
		for (Iterator iterator = taskList.getAllSubTasks(null).iterator(); iterator.hasNext();) {
			writeToDoc = taskGenerator(writeToDoc, (Task)iterator.next());    
			writeToDoc += "<br>";
		}

		writeToDoc +="</body></html>";
		return writeToDoc;
	}
	
	
	/**
	 * 
	 * @param taskConversion
	 * @param task
	 * @return
	 */
	private String taskGenerator (String taskConversion, Task task) {
		
		taskConversion += "<strong>"+task.getText()+"</strong>";
		if(task.getEndDate() == null) {
			taskConversion += "<p>Duration: "+task.getStartDate().toString()+ "None +</p>";
		} else {
			taskConversion += "<p>Duration: " +task.getStartDate().toString()+ " - "+task.getEndDate().toString()+"</p>";
		}
		
		int status = task.getStatus(CurrentDate.get());
		if(status == 0){
			taskConversion += "<strong>Status: </strong>" + "Scheduled &nbsp &nbsp &nbsp &nbsp  &nbsp &nbsp &nbsp &nbsp" + "<strong> Progress: </strong>" +task.getProgress()+ "</p>";		
		} else if (status == 1) {
			taskConversion += "<strong>Status: </strong>" + "Active &nbsp &nbsp &nbsp &nbsp  &nbsp &nbsp &nbsp &nbsp" + "<strong> Progress: </strong>" +task.getProgress()+ "</p>";		
		} else if (status == 2) {
			taskConversion += "<strong>Status: </strong>" + "Complete &nbsp &nbsp &nbsp &nbsp  &nbsp &nbsp &nbsp &nbsp" + "<strong> Progress: </strong>" +task.getProgress()+ "</p>";		
		} else if (status == 4) {
			taskConversion += "<strong>Status: </strong>" + "Frozen &nbsp &nbsp &nbsp &nbsp  &nbsp &nbsp &nbsp &nbsp" + "<strong> Progress: </strong>" +task.getProgress()+ "</p>";		
		} else if (status == 5) {
			taskConversion += "<strong>Status: </strong>" + "Failed &nbsp &nbsp &nbsp &nbsp  &nbsp &nbsp &nbsp &nbsp" + "<strong> Progress: </strong>" +task.getProgress()+ "</p>";		
		}else if (status == 6) {
			taskConversion += "<strong>Status: </strong>" + "Locked &nbsp &nbsp &nbsp &nbsp  &nbsp &nbsp &nbsp &nbsp" + "<strong> Progress: </strong>" +task.getProgress()+ "</p>";		
		}else {
			taskConversion += "<strong>Status: </strong>" + "Deadline &nbsp &nbsp &nbsp &nbsp  &nbsp &nbsp &nbsp &nbsp" + "<strong> Progress: </strong>" +task.getProgress()+ "</p>";		
		}
		
		
		int priority = task.getPriority();
		if(priority == 0){
			taskConversion += "Priority: <font color=\"#666699\">"+Local.getString("Lowest")+"</font>";
		} else if (priority == 1) {
			taskConversion += "Priority: <font color=\"#3333CC\">"+Local.getString("Low")+"</font>";
		} else if (priority == 2) {
			taskConversion += "Priority: <font color=\"green\">"+Local.getString("Normal")+"</font>";
		} else if (priority == 3) {
			taskConversion += "Priority: <font color=\"#FF9900\">"+Local.getString("High")+"</font>";
		}else {
			taskConversion += "Priority: <font color=\"red\">"+Local.getString("Highest")+"</font>";
		}
		taskConversion += "<br>";
		taskConversion += "<br>";
		taskConversion += "<u>Description</u>";
		taskConversion += "<p> &nbsp &nbsp &nbsp &nbsp"+task.getDescription()+"</p>";
		return taskConversion;
	}
	
	
	/**
	 * 
	 * @param noteConversion
	 * @param note
	 * @return
	 */
	private String noteGenerator (String noteConversion, Note note) {
		noteConversion += "<b>   "+note.getTitle()+"</b>";
		noteConversion += "<p> &nbsp &nbsp &nbsp Date Written: "+note.getDate()+"</p>";
	
		return noteConversion;
		
	}
	

}
