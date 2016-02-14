package net.sf.memoranda.util;

import net.sf.memoranda.*;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.ui.*;
import net.sf.memoranda.ui.htmleditor.AltHTMLWriter;
import net.sf.memoranda.util.PriorityQueue;
import nu.xom.Element;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;




import javax.swing.text.html.HTMLDocument;

/**
 * This class function is to provide the details need to generate
 * a report from the current project. This class takes in the requested
 * information and then turns the information into a report. The report 
 * is generated in HTML. Currently only Notes are generated in the report,
 * but later Tasks, events, and resources will be added. 
 * 
 * @author BrandonGrow
 * @version 1.0 Feb 13, 2015
 *
 */
public class ReportExporter {

    static boolean _chunked = false;
    static boolean _num = false;
    static boolean _xhtml = false;
    static boolean _copyImages = false;
    static File output = null;
    static String _charset = null;
    static boolean _titlesAsHeaders = false;
    static boolean _navigation = false;
    
    static String charsetString = "\n";

    
    
    /**
     * This method creates the actual report itself. The file is generated
     * here, which is called "Project Report". This method also calls upon 
     * the other methods for the text to be generated into HTML.
     * 
     * 
     * @param prj  --gets the current project
     * @param f  -- file name
     * @param charset  --sets the characters
     * @param xhtml  --HTML to xHTML 
     * @param chunked  --parts of the note
     * @param navigation  --looking for where the project directory is
     * @param titlesAsHeaders  --tile as the header of project
     */
    public static void export(Project prj, File f, String charset, boolean xhtml, boolean chunked) {

     
        _chunked = chunked;
        _charset = charset;
        _xhtml = xhtml;
  
        if (f.isDirectory()) {
            output = new File(f.getPath() + "/Project Report.html");
        }
        else {
            output = f;
        }
        
        NoteList nl = CurrentStorage.get().openNoteList(prj);
        Vector notes = (Vector) nl.getAllNotes(); 



       //Creates Labels for the HTML output for each section.
       String notesLabelHTML = "Notes";
       String tasksLabelHTML = "Tasks";
       String eventsLabHTML = "Events";
 
        
        //NotesVectorSorter.sort(notes);
        Collections.sort(notes);

  

        Writer fw;

        if (output.getName().indexOf(".htm") == -1) {
            String dir = output.getPath();
            String ext = ".html";

            String nfile = dir + ext;

            output = new File(nfile);
        }        
        try {
            if (charset != null) {
                fw = new OutputStreamWriter(new FileOutputStream(output),
                        charset);
                charsetString = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
                        + charset + "\" />";
            }
            else
                fw = new FileWriter(output);
        }
        catch (Exception ex) {
            new ExceptionDialog(ex, "Failed to write to " + output, "");
            return;
        }
        
        //Writes the title and the notes section of the HTMl Report
        write(fw, "<html>\n<head>\n" + charsetString + "<title>"
                + prj.getTitle()
                + "</title>\n</head>\n<body>\n<h1 class=\"projecttitle\">" 
                + prj.getTitle() +  "</h1>\n" +"\n<br>\n" 
                + "</title>\n</head>\n<body>\n<h2 class=\"projecttitle\">"
                + notesLabelHTML + "</h2>\n" );
        generateChunks(fw, notes);
        
        //Writes the Task section of the HTML Report
        write(fw, "\n<hr></hr><a" +"</title>\n</head>\n<body>\n<h2 class=\"projecttitle\">" + "\n<br>\n"
                + tasksLabelHTML + "</h2>\n" );
        
        //writes the Events section of the HTML Report
        write(fw, "\n<hr></hr><a" +"</title>\n</head>\n<body>\n<h2 class=\"projecttitle\">" + "\n<br>\n"
                + eventsLabHTML + "</h2>\n" );
    
        //Writes the ending of the report with the data and time
        write(fw, "\n<hr></hr><a "
                + "\n<br></br>\n" + new Date().toString()
                + "\n</body>\n</html>");
        try {
            fw.flush();
            fw.close();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex, "Failed to write to " + output, "");
        }
    }

    

    /**
     * This method allows for the note to be converted to HTML status. Each 
     * notes is converted here, which is referred to as the body.
     * 
     * 
     * @param note  --takes in the note from the list of the project
     * @return  --returns the test of the note(s) in html
     */
    
   
    public static String getNoteHTML(Note note) {
        String text = "";
        StringWriter sw = new StringWriter();
        AltHTMLWriter writer = new AltHTMLWriter(sw,
                (HTMLDocument) CurrentStorage.get().openNote(note), _charset,
                _num);
        try {
            writer.write();
            sw.flush();
            sw.close();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
        text = sw.toString();
        if (_xhtml) {
            text = HTMLFileExport.convertToXHTML(text);
        }
        
        text = Pattern
                .compile("<body(.*?)>", java.util.regex.Pattern.DOTALL
                        + java.util.regex.Pattern.CASE_INSENSITIVE).split(text)[1];
        text = Pattern
                .compile("</body>", java.util.regex.Pattern.DOTALL
                        + java.util.regex.Pattern.CASE_INSENSITIVE).split(text)[0];
    
        text = "<div class=\"note\">" + text + "</div>";

        if (_titlesAsHeaders) {
                        text = "\n\n<div class=\"date\">"
                    + note.getDate().getFullDateString()
                    + ":</div>\n<h1 class=\"title\">" + note.getTitle()
                    + "</h1>\n" + text;
        }
        
        return text;
    }

    
    /**
     * This method allows for the text to be orderly placed on the
     * HTML report. 
     * 
     * @param prev  --previous line
     * @param next  --next line
     * @return  Returns the final text outline
     */

    private static String generateNav(Note prev, Note next) {
        String s = "<hr></hr><div class=\"navigation\"><table border=\"0\" width=\"100%\" cellpadding=\"2\"><tr><td width=\"33%\">";
        if (prev != null)  {  
            s += "<div class=\"navitem\"><a href=\"" + prev.getId() + ".html\">"
                    + Local.getString("Previous") + "</a><br></br>"
                    + prev.getDate().getMediumDateString() + " "
                    + prev.getTitle() + "</div>";
        }
        
        else {
            s += "&nbsp;";
                s += "</td><td width=\"34%\" align=\"center\"><a href=\""
                + output.getName()
                + "\">Up</a></td><td width=\"33%\" align=\"right\">";
        }
        
        if (next != null) {
            s += "<div class=\"navitem\"><a href=\"" + next.getId() + ".html\">"
                    + Local.getString("Next") + "</a><br></br>"
                    + next.getDate().getMediumDateString() + " "
                    + next.getTitle() + "</div>";
        }
            
        else {
            s += "&nbsp;";
        }
        s += "</td></tr></table></div>\n";
        return s;
    }
    
    
    /**
     * Here the method takes in the Notes that have been converted to html
     * and allows for the file to be created and then the notes added to the 
     * report. Here we actual write to the HTMl file itself, by using the 
     * HTML that was generated from getNoteHTML method.
     * 
     * @param w  --writes the notes to the HTMl doc
     * @param notes  --gets the notes as a vector
     */

    private static void generateChunks(Writer w, Vector notes) {
        Object[] n = notes.toArray();
        for (int i = 0; i < n.length; i++) {
            Note note = (Note) n[i];
            CalendarDate d = note.getDate();
            if (_chunked) {
                File f = new File(output.getParentFile().getPath() + "/"
                        + note.getId()
                        + ".html");
                Writer fw = null;
                try {
                    if (_charset != null) {
                        fw = new OutputStreamWriter(new FileOutputStream(f),
                                _charset);
                    }
                    
                    else {
                        fw = new FileWriter(f);
                    }
                    
                    String s = "<html>\n<head>\n"+charsetString+"<title>" + note.getTitle()
                            + "</title>\n</head>\n<body>\n" + getNoteHTML(note);
                    
                    if (_navigation) {
                        Note nprev = null;
                        if (i > 0) {
                            nprev = (Note) n[i - 1];
                        }
                        
                        Note nnext = null;
                        if (i < n.length - 1) {
                            nnext = (Note) n[i + 1];
                        }
                        
                        s += generateNav(nprev, nnext);
                    }
                    s += "\n</body>\n</html>";
                    fw.write(s);
                    fw.flush();
                    fw.close();
                }
                catch (Exception ex) {
                    new ExceptionDialog(ex, "Failed to write to " + output, "");
                }
            }
            else {
                write(w, "<a name=\""  + "\">" + note.getDate() +"</a>\n" + getNoteHTML(note) + "</a>\n");
            }
        }
    }
 
    /**
     * The writer that is used to write the text onto the report,
     * which is used mostly in the generateChucks method.
     */
 
    private static void write(Writer w, String s) {
        try {
            w.write(s);
        }
        catch (Exception ex) {
            new ExceptionDialog(ex, "Failed to write to " + output, "");
        }
    }
    
    
   
   
}