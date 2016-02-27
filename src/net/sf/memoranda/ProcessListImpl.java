/**
 * ProcessListImpl.java
 * Created on 23 Feb. 2016
 * Package: net.sf.memoranda
 * 
 * Process List Implementation Class.
 * @author Meyung
 */

package net.sf.memoranda;

import java.util.Vector;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

public class ProcessListImpl implements ProcessList {

	private Project _project = null;
	private Document _doc = null;
	private Element _root = null;


	/**
	 * Constructor for ProcessListImpl.
	 * @param doc
	 * @param prj
	 */
	public ProcessListImpl(Document doc, Project prj) {
		_doc = doc;
		_root = _doc.getRootElement();
		_project = prj;
	}

	public ProcessListImpl(Project prj) {            
		_root = new Element("process-list");
		_doc = new Document(_root);
		_project = prj;
	}	

	public Vector<ProcessImpl> getAllProcesses() {
		Vector<ProcessImpl> processVector = new Vector<ProcessImpl>();
		Elements cs = _root.getChildElements("process");
		for (int i = 0; i < cs.size(); i++) {
			processVector.add(new ProcessImpl(cs.get(i).getAttribute("processName").getValue(),
					cs.get(i).getAttribute("scrumMaster").getValue(), cs.get(i).getAttribute("productOwner").getValue(),
					cs.get(i).getAttribute("teamMembers").getValue(),cs.get(i).getAttribute("sprintPlanningMeeting").getValue(),
					cs.get(i).getAttribute("dailyStandUp").getValue(),cs.get(i).getAttribute("sprintReview").getValue(),
					cs.get(i).getAttribute("sprintRetrospective").getValue()));
		}
		return processVector;
	}

	public Process getProcess(String processName) {
		Elements cs = _root.getChildElements("process");
		for (int i = 0; i < cs.size(); i++)
			if (cs.get(i).getAttribute("processName").getValue().equals(processName))
				return new ProcessImpl(cs.get(i).getAttribute("processName").getValue(),
						cs.get(i).getAttribute("scrumMaster").getValue(), cs.get(i).getAttribute("productOwner").getValue(),
						cs.get(i).getAttribute("teamMembers").getValue(),cs.get(i).getAttribute("sprintPlanningMeeting").getValue(),
						cs.get(i).getAttribute("dailyStandUp").getValue(),cs.get(i).getAttribute("sprintReview").getValue(),
						cs.get(i).getAttribute("sprintRetrospective").getValue());
		return null;
	}

	public void addProcess(String processName, String scrumMaster, String productOwner, String teamMembers, String sprintPlanningMeeting, String dailyStandUp, String sprintReview, String sprintRetrospective) {
		Element el = new Element("process");
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("processName", processName));  
		el.addAttribute(new Attribute("scrumMaster", scrumMaster));
		el.addAttribute(new Attribute("productOwner", productOwner));
		el.addAttribute(new Attribute("teamMembers", teamMembers));
		el.addAttribute(new Attribute("sprintPlanningMeeting", sprintPlanningMeeting));
		el.addAttribute(new Attribute("dailyStandUp", dailyStandUp));
		el.addAttribute(new Attribute("sprintReview", sprintReview));
		el.addAttribute(new Attribute("sprintRetrospective", sprintRetrospective));
		_root.appendChild(el);
	}

	public void addProcess(String processName) {
		addProcess(processName, "", "", "", "", "", "", "");
	}

	public void removeProcess(String processName) {
		Elements cs = _root.getChildElements("process");
		for (int i = 0; i < cs.size(); i++)
			if (cs.get(i).getAttribute("processName").getValue().equals(processName)) {
				_root.removeChild(cs.get(i));
			}
	}

	public int getAllProcessCount() {
		return _root.getChildElements("process").size();
	}

	public Document getXMLContent() {
		return _doc;
	}

}
