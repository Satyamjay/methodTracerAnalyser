package parser;

// A helper class to store information of threads
public class ThreadInfo {
	private String threadID;
	private String threadName;
	public ThreadInfo(String tid, String tname){
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
}
