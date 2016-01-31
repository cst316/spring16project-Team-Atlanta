package net.sf.memoranda;

public interface Process {
	
    public static final int PRIORITY_LOWEST = 0;
    
    public static final int PRIORITY_LOW = 1;
    
    public static final int PRIORITY_NORMAL = 2;
    
    public static final int PRIORITY_HIGH = 3;
    
    public static final int PRIORITY_HIGHEST = 4;
	
	public static String DEFAULT_PROCESS_NAME = "GENERIC_PROCESS";
	
	boolean addTaskToProcess(Task task);
	boolean removeTaskFromProcess(Task task);
    
    int getPriority();
    boolean setPriority(int priority);
    
    String getProcessName();
    boolean setProcessName(String name);
	String getID();
	
}
