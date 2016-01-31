/**
 * TaskListImpl.java
 * Created on 21.02.2003, 12:29:54 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;
//import nu.xom.converters.*;
//import org.apache.xerces.dom.*;
//import nux.xom.xquery.XQueryUtil;

/**
 * 
 */
public class ProcessListImpl implements ProcessList {

    private Project _project = null;
    private Document _doc = null;
    private Element _root = null;
	
	/*
	 * Hastable of "task" XOM elements for quick searching them by ID's
	 * (ID => element) 
	 */
	private Hashtable elements = new Hashtable();
    
    /**
     * Constructor for ProcessListImpl.
     */
    public ProcessListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
		buildElements(_root);
    }
    
    public ProcessListImpl(Project prj) {            
            _root = new Element("tasklist");
            _doc = new Document(_root);
            _project = prj;
    }
    
	public Project getProject() {
		return _project;
	}
		
	/*
	 * Build the hashtable recursively
	 */
	private void buildElements(Element parent) {
		Elements els = parent.getChildElements("task");
		for (int i = 0; i < els.size(); i++) {
			Element el = els.get(i);
			elements.put(el.getAttribute("id").getValue(), el);
			buildElements(el);
		}
	}

    public Process createProcess(CalendarDate startDate, CalendarDate endDate, String text, int priority, long effort, String description, String parentProcessId) {
        Element el = new Element("task");
        el.addAttribute(new Attribute("startDate", startDate.toString()));
        el.addAttribute(new Attribute("endDate", endDate != null? endDate.toString():""));
		String id = Util.generateId();
        el.addAttribute(new Attribute("id", id));
        el.addAttribute(new Attribute("progress", "0"));
        el.addAttribute(new Attribute("effort", String.valueOf(effort)));
        el.addAttribute(new Attribute("priority", String.valueOf(priority)));
                
        Element txt = new Element("text");
        txt.appendChild(text);
        el.appendChild(txt);

        Element desc = new Element("description");
        desc.appendChild(description);
        el.appendChild(desc);

        if (parentProcessId == null) {
            _root.appendChild(el);
        }
        else {
            Element parent = getProcessElement(parentProcessId);
            parent.appendChild(el);
        }
        
		elements.put(id, el);
		
        Util.debug("Created process with parent " + parentProcessId);
        
        return new ProcessImpl(el, this);
    }
	
	/**
     * @see net.sf.memoranda.TaskList#removeTask(import net.sf.memoranda.Task)
     */

    public void removeProcess(Process process) {
        elements.remove(process.getID());
    }

    public Process getProcess(String id) {
        Util.debug("Getting process " + id);          
        return new ProcessImpl(getProcessElement(id), this);          
    }
    
	    /**
     * @see net.sf.memoranda.TaskList#getXMLContent()
     */	 
    public Document getXMLContent() {
        return _doc;
    }
          
    /*
     * private methods below this line
     */
    private Element getProcessElement(String id) {
		Element el = (Element)elements.get(id);
		if (el == null) {
			Util.debug("Process " + id + " cannot be found in project " + _project.getTitle());
		}
		return el;
    }
    
    private Collection getAllRootProcesses() {
        Elements processes = _root.getChildElements("process");
        return convertToProcessObjects(processes);    	    		
    }
    
    private Collection convertToProcessObjects(Elements processes) {
        Vector v = new Vector();

        for (int i = 0; i < processes.size(); i++) {
            Process t = new ProcessImpl(processes.get(i), this);
            v.add(t);
        }
        return v;
    }

    private boolean isActive(Task t,CalendarDate date) {
    	if ((t.getStatus(date) == Task.ACTIVE) || (t.getStatus(date) == Task.DEADLINE) || (t.getStatus(date) == Task.FAILED)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

	public Collection getTopLevelTasks() {
		// TODO Auto-generated method stub
		return null;
	}


}
