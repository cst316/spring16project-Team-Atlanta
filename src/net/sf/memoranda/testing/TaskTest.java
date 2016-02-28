package net.sf.memoranda.testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import net.sf.memoranda.*;
import net.sf.memoranda.date.CalendarDate;
import nu.xom.Document;
import nu.xom.Element;



public class TaskTest {

	
	private TaskListImpl testList;
	private TaskImpl test1;
	private TaskImpl test2;
	
	
	
	@Before
	public void setUpOnce() throws Exception {
		ProjectManager.createProject("test", new CalendarDate(10,15,2012), 
				      new CalendarDate(18,8,2016));
		Project test = ProjectManager.getProject("test");
		testList = new TaskListImpl(new Document(new Element("document")), test);
		test1 = (TaskImpl) testList.createTask(new CalendarDate(1,18,2015), 
				new CalendarDate(7,13,2016), "Finish CST 316 Lab", 
				Task.PRIORITY_HIGH, Task.ACTIVE, "Finish JUnit lab parts 2.", null);
		test2 = (TaskImpl)testList.createTask(new CalendarDate(2,2,2016), 
				new CalendarDate(2,3,2016), "Study for Final Exam", 
				Task.PRIORITY_NORMAL, Task.COMPLETE, 
				"Focus on weeks 2 and 3 for final.", test1.getID());
	}
		
	/**
	 * Tests the date aspect for a given task. Finds the longest end date of the tasks
	 * created and checks to make sure the dates match up for each task.
	 */
	@Test
	public void testStartAndEndDates() {
		assertEquals(test1.equals(test1.getStartDate()),equals(test1.getStartDate()));
		assertEquals(test2.equals(test2.getStartDate()),equals(test2.getStartDate()));
		assertEquals(test2.equals(test2.getEndDate()),equals(test2.getEndDate()));
		assertTrue(testList.getLatestEndDateFromSubTasks(test1).equals(test1.getEndDate()));

	}
	
	
	/**
	 * Tests the parent tasks.
	 */
	@Test
	public void testParentTest(){
		assertEquals(test1.equals(test1.getParentTask()),equals(test1.getParentTask()));
	}
	
	/**
	 * Test the output of the status of each task. 
	 */
	@Test
	public void testStatus(){
		assertEquals(test1.equals(test1.getStatus(test1)),equals("Active"));
		assertEquals(test2.equals(test2.getStatus(test2)),equals("Complete"));
	}
	
	/**
	 * Tests the priority of each task. 
	 */
	@Test
	public void testPriority(){
		assertEquals(test1.equals(test1.getPriority()),equals("High"));
		assertEquals(test2.equals(test2.getPriority()),equals("Normal"));
	}
	
	
	/**
	 * Tests the progress of the task. 
	 */
	@Test
	public void testProgress(){
		assertEquals(test1.equals(test1.getProgress()),equals("0"));
		assertEquals(test2.equals(test2.getProgress()),equals("0"));
	}
	
	
}
