package parser;
import parser.Parser;

public class Method {
	private String methodName;
	private String className;
	private String threadId;
	private String startTime;
	private String endTime = "";
	private boolean staticOrNot;	// Set True if method is static
	private String thisPointer;
	private double runTime;
	
	public Method(String mName, String cName, String tid, String sTime, boolean sOrNot){
		this.setMethodName(mName);
		this.className = cName;
		this.threadId = tid;
		this.startTime = sTime;
		this.staticOrNot = sOrNot;
		this.endTime = "";
	}
	
	public Method(String mName, String cName, String tid, String sTime, boolean sOrNot, String tPointer){
		this(mName, cName, tid, sTime, sOrNot);
		this.thisPointer = tPointer;
	}


	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public boolean isStaticOrNot() {
		return staticOrNot;
	}
	public void setStaticOrNot(boolean staticOrNot) {
		this.staticOrNot = staticOrNot;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getThisPointer() {
		return thisPointer;
	}
	public void setThisPointer(String thisPointer) {
		this.thisPointer = thisPointer;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	public double getRuntime(){
		return runTime;
	}
	
	// Tells if the method ended its execution or not according to the log file
	public boolean hasEnded(){
		if(endTime.equals("")){
			return false;
		}
		else{
			return true;
		}
	}
	// Calculate total runtime of the method
	public void calculateRuntime(){
		if(!endTime.equals("")){
			runTime = Parser.timeDifference(startTime, endTime);
		}
		else{
			runTime = -1; 					// Set runtime to -1 if the method never ended
		}
	}
	
}
