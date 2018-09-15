package parser;
import java.util.Stack;

import parser.Parser;

public class Method {
	private static int numberOfMethods = 1;
	private String methodName;
	private String className;
	private String threadId;
	private String startTime;
	private String parameters[];
	private String returnType;
	private String endTime = "";
	private boolean staticOrNot;	// Set True if method is static
	private String thisPointer;
	private double runTime;
	private int id;
	private Stack<String> methodStack = new Stack<>();

	
	public Method(String mName, String cName, String tid, String sTime, boolean sOrNot, String[] params, String rType){
		this.methodName = mName;
		this.parameters = params;
		this.returnType = rType;
		this.className = cName;
		this.threadId = tid;
		this.startTime = sTime;
		this.staticOrNot = sOrNot;
		this.endTime = "";
		this.setId(numberOfMethods);
		numberOfMethods++;
	}
	
	public Method(String mName, String cName, String tid, String sTime, boolean sOrNot, String tPointer, String[] params, String rType){
		this(mName, cName, tid, sTime, sOrNot, params, rType);
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

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String[] getParameters() {
		return parameters;
	}

	public void setParameters(String parameters[]) {
		this.parameters = parameters;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Stack<String> getMethodStack() {
		return methodStack;
	}
	
	public void pushInMethodStack(String s){
		methodStack.push(s);
	}
	
}
