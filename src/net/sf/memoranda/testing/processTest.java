package net.sf.memoranda.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import net.sf.memoranda.ProcessImpl;
import net.sf.memoranda.ProcessList;
import net.sf.memoranda.ProcessListImpl;
import net.sf.memoranda.Project;

public class processTest {
	private ProcessImpl process1 = null;
	private Project _project = null;
	private ProcessList processList1 = null;
	
	@Before
	public void setUp() throws Exception {
		process1 = new ProcessImpl("Process Name","Scrum Master","Product Owner","Team Members","Sprint Planning Meeting","Daily StandUp","Sprint Review","Sprint Retrospective");
		processList1 = new ProcessListImpl(_project);
		processList1.addProcess("Process Name Test","Scrum Master Test","Product Owner Test","Team Members Test","Sprint Planning Meeting Test","Daily StandUp Test","Sprint Review Test","Sprint Retrospective Test");
	}

	/**
	 * Test method for {@link net.sf.memoranda.Process#Process()}
	 * This test method ensures that the Process object is properly
	 * instantiated.
	 */
	@Test
	public final void testContactObject() {
		assertNotNull(process1);
	}

	/**
	 * Test method for {@link net.sf.memoranda.Process#getProcessName()},
	 * {@link net.sf.memoranda.Process#getScrumMaster()}, {@link net.sf.memoranda.Process#getProductOwner()},
	 * {@link net.sf.memoranda.Process#getTeamMembers()}, {@link net.sf.memoranda.Process#getSprintPlanningMeeting()},
	 * and {@link net.sf.memoranda.Process#getDailyStandUp()}, {@link net.sf.memoranda.Process#getSprintReview()},
	 * and {@link net.sf.memoranda.Process#getSprintRetrospective()}
	 * This test method ensures that the Process object getters are returning
	 * the proper values based on the instantiated object.
	 */
	@Test
	public final void testProcessElementAttributes() {
		assertEquals(process1.getProcessName(),"Process Name");
		assertEquals(process1.getScrumMaster(),"Scrum Master");
		assertEquals(process1.getProductOwner(),"Product Owner");
		assertEquals(process1.getTeamMembers(),"Team Members");
		assertEquals(process1.getSprintPlanningMeeting(),"Sprint Planning Meeting");
		assertEquals(process1.getDailyStandUp(),"Daily StandUp");
		assertEquals(process1.getSprintReview(),"Sprint Review");
		assertEquals(process1.getSprintRetrospective(),"Sprint Retrospective");
	}
	
	/**
	 * Test method for {@link net.sf.memoranda.ProcessesListImpl#ProcessList()}
	 * This test method ensures that the ProcessList element is properly
	 * instantiated.
	 */
	@Test
	public final void testProcessListObject() {
		assertNotNull(processList1.getProcess("Process Name Test"));
		assertTrue(processList1.getAllProcessCount() == 1);
	}
	
	/**
	 * Test method for {@link net.sf.memoranda.ProcessListImpl#removeProcess()}
	 * This test method ensures that the ContactList element is properly
	 * removed and all appropriate methods are updated accordingly.
	 */
	@Test
	public final void testRemoveProcessFromList() {
		processList1.removeProcess("Process Name Test");
		assertTrue(processList1.getAllProcessCount() == 0);
	}
}