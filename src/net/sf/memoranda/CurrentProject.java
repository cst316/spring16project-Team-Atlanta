/**
 * CurrentProject.java
 * Created on 13.02.2003, 13:16:52 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 *
 */
package net.sf.memoranda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

import net.sf.memoranda.ui.AppFrame;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Storage;

/**
 *
 */
/*$Id: CurrentProject.java,v 1.6 2005/12/01 08:12:26 alexeya Exp $*/
public class CurrentProject {

    private static Project _project = null;
    private static TaskList _tasklist = null;
    private static ProcessList _processlist = null;
    private static NoteList _notelist = null;
    private static ResourcesList _resources = null;
    private static ContactsList _contacts = null;
    private static Vector projectListeners = new Vector();

        
    static {
        String prjId = (String)Context.get("LAST_OPENED_PROJECT_ID");
        if (prjId == null) {
            prjId = "__default";
            Context.put("LAST_OPENED_PROJECT_ID", prjId);
        }
        //ProjectManager.init();
        _project = ProjectManager.getProject(prjId);
		
		if (_project == null) {
			// alexeya: Fixed bug with NullPointer when LAST_OPENED_PROJECT_ID
			// references to missing project
			_project = ProjectManager.getProject("__default");
			if (_project == null) 
				_project = (Project)ProjectManager.getActiveProjects().get(0);						
            Context.put("LAST_OPENED_PROJECT_ID", _project.getID());
			
		}		
		
        _tasklist = CurrentStorage.get().openTaskList(_project);
        _processlist = CurrentStorage.get().openProcessList(_project);
        _notelist = CurrentStorage.get().openNoteList(_project);
        _resources = CurrentStorage.get().openResourcesList(_project);
        _contacts = CurrentStorage.get().openContactsList(_project);
        AppFrame.addExitListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();                                               
            }
        });
    }
        

    public static Project get() {
        return _project;
    }

    public static TaskList getTaskList() {
            return _tasklist;
    }

    public static ProcessList getProcessList() {
        return _processlist;
    }

    public static NoteList getNoteList() {
            return _notelist;
    }
    
    public static ResourcesList getResourcesList() {
            return _resources;
    }
    
    public static ContactsList getContactsList() {
        return _contacts;
    }

    public static void set(Project project) {
        if (project.getID().equals(_project.getID())) return;
        TaskList newtasklist = CurrentStorage.get().openTaskList(project);
        ProcessList newprocesslist = CurrentStorage.get().openProcessList(project);
        NoteList newnotelist = CurrentStorage.get().openNoteList(project);
        ResourcesList newresources = CurrentStorage.get().openResourcesList(project);
        ContactsList newcontacts = CurrentStorage.get().openContactsList(project);
        notifyListenersBefore(project, newnotelist, newtasklist, newresources, newcontacts);
        _project = project;
        _tasklist = newtasklist;
        _processlist = newprocesslist;
        _notelist = newnotelist;
        _resources = newresources;
        _contacts = newcontacts;
        notifyListenersAfter();
        Context.put("LAST_OPENED_PROJECT_ID", project.getID());
    }

    public static void addProjectListener(ProjectListener pl) {
        projectListeners.add(pl);
    }

    public static Collection getChangeListeners() {
        return projectListeners;
    }

    private static void notifyListenersBefore(Project project, NoteList nl, TaskList tl, ResourcesList rl, ContactsList cl) {
        for (int i = 0; i < projectListeners.size(); i++) {
            ((ProjectListener)projectListeners.get(i)).projectChange(project, nl, tl, rl, cl);
            /*DEBUGSystem.out.println(projectListeners.get(i));*/
        }
    }
    
    private static void notifyListenersAfter() {
        for (int i = 0; i < projectListeners.size(); i++) {
            ((ProjectListener)projectListeners.get(i)).projectWasChanged();            
        }
    }

    public static void save() {
        Storage storage = CurrentStorage.get();

        storage.storeNoteList(_notelist, _project);
        storage.storeTaskList(_tasklist, _project); 
        storage.storeProcessList(_processlist, _project);
        storage.storeResourcesList(_resources, _project);
        storage.storeContactsList(_contacts, _project);
        storage.storeProjectManager();
    }
    
    public static void free() {
        _project = null;
        _tasklist = null;
        _processlist = null;
        _notelist = null;
        _resources = null;
        _contacts = null;
    }
}
