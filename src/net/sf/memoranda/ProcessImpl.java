package net.sf.memoranda;

import java.util.ArrayList;

import nu.xom.Attribute;
import nu.xom.Element;

@SuppressWarnings("rawtypes")
public class ProcessImpl implements Process, Comparable {

	private Element processElement = null;
	private ProcessList processList = null;
	private ArrayList taskList = new ArrayList();
	
	/**
     * Constructor for DefaultProcess.
	 * @return 
     */
    public ProcessImpl(Element processElement, ProcessList tl) {
    	this.processElement = processElement;
        processList = tl;
    }

	/**
     * 
     * @return processElement
     */
    public Element getProcessElement() {
    	return processElement;
    }

	public int compareTo(Object o) {
		/*Task task = (Task) o;
	 	if(getRate() > task.getRate())
			return 1;
		else if(getRate() < task.getRate())
			return -1;
		else 
			return 0;*/
		return 0;
	}

	/**
	 * 
	 * @return boolean value
	 */
	@SuppressWarnings("unchecked")
	public boolean addTaskToProcess(Task task) {
		if(taskList.contains(task)) {
			return false;
		}
		else{
			taskList.add(task);
		}
		return true;
	}

	/**
	 * @return boolean value
	 */
	public boolean removeTaskFromProcess(Task task) {
		if(taskList.contains(task)) {
			taskList.remove(task);
			return true;
		}
		return false;
	}

	public int getPriority() {
		 Attribute pa = processElement.getAttribute("priority");
	        if (pa == null){
	            return Task.PRIORITY_NORMAL;
	        }
	        return new Integer(pa.getValue()).intValue();
	}

	public boolean setPriority(int priority) {
		setAttr("priority", String.valueOf(priority));
		return true;
	}

	public String getProcessName() {
		 Attribute pa = processElement.getAttribute("processname");
	        if (pa == null){
	            return Process.DEFAULT_PROCESS_NAME;
	        }
	        return new String(pa.getValue());
	}

	public boolean setProcessName(String name) {
		setAttr("processName", String.valueOf(name));
		return true;
	}
	
    private void setAttr(String a, String value) {
        Attribute attr = processElement.getAttribute(a);
        if (attr == null)
           processElement.addAttribute(new Attribute(a, value));
        else
            attr.setValue(value);
    }
    
    public String getID() {
        return processElement.getAttribute("id").getValue();
    }
}
