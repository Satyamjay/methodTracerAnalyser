package parser;

import java.util.ArrayList;
import java.util.List;

import parser.Method;
// A helper class to store information of threads
public class Threads {
	private String threadID;
	private String threadName;
	private List<Method> methods = new ArrayList<>();
	public Threads(String tid, String tname){
		this.setThreadID(tid);
		this.setThreadName(tname);
	}
	public String getThreadName() {
		return threadName;
	}
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	public String getThreadID() {
		return threadID;
	}
	public void setThreadID(String threadID) {
		this.threadID = threadID;
	}
	public List<Method> getMethods() {
		return methods;
	}
	public void addMethod(Method method) {
		methods.add(method);
	}
}
