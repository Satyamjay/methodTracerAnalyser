/**
 * 
 */
package parser;
import parser.Method;

import java.util.Comparator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.Threads;

import java.util.HashMap;

/**
 ** @author satyam
 **
 **/
// This class will extract useful information from the trace file
public class Parser{
	// Contains all the threads logged in the file
	// and the threads contain their methods
	private HashMap<String, Threads> activeThreads;
	// Contains start-time and end-time of the trace
	private double traceTime; // in seconds 
	public Parser(File fileName) throws InvalidLogFileException{
		Scanner sc = null;
		try{
			sc = new Scanner(fileName); 
			activeThreads = getThreadInfo(sc);			// First scan for thread names
			traceTime = getTraceTime(sc);				// Then pass the scanner object to find the time
			scanMethods(sc);							// Then pass the scanner object to scan the methods
			System.out.println(traceTime);
		}
		catch(IOException e){
			System.out.println("File Not Found");
		}
	}


	// This method gets all the threads listed in the log file
	private HashMap<String, Threads> getThreadInfo(Scanner sc) throws InvalidLogFileException{
		boolean found = false;
		Pattern pat = Pattern.compile("\\s{8}(0x[a-fA-F0-9]+)\\s{2}(.+)"); // Pattern example:- [        0x26e0500  main]
		// Find Active Threads line in the file
		while(sc.hasNextLine()){
			found = Pattern.matches("Active Threads :", sc.nextLine());
			if(found){
				break;
			}
		}
		// If found, Scan next lines and get the id and name of the thread using regex group
		if(found){
			HashMap<String, Threads> threadInfoList = new HashMap<>();
			while(sc.hasNextLine()){
				String nextLine = sc.nextLine();
				Matcher m = pat.matcher(nextLine);
				if(m.matches()){
					threadInfoList.put(m.group(1),(new Threads(m.group(1),m.group(2))));
				}
				else{
					break;
				}
			}
			return threadInfoList;
		}
		throw new InvalidLogFileException("Invalid Log file");
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	// Get trace time according to log file
	private double getTraceTime(Scanner sc) throws InvalidLogFileException{
		Pattern pat1 = Pattern.compile("First tracepoint\\s{0,}:\\s{0,9}([0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{9})"); // Pattern example:- [First Tracepoint : 19:04:23.947000000
		Pattern pat2 = Pattern.compile("Last tracepoint\\s{0,}:\\s{0,9}([0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{9})"); // Pattern example:- [Last Tracepoint : 19:04:23.947000000
		String firstTracepoint = "";
		String lastTracePoint = "";
		while(sc.hasNextLine()){
			String nextLine = sc.nextLine();
			Matcher m = pat1.matcher(nextLine);
			if(m.matches()){
				firstTracepoint = m.group(1);
				break;
			}
		}
		while(sc.hasNextLine()){
			String nextLine = sc.nextLine();
			Matcher m = pat2.matcher(nextLine);
			if(m.matches()){
				lastTracePoint = m.group(1);
				break;
			}
		}
		if(!sc.hasNextLine()){
			throw new InvalidLogFileException("Invalid Log file");
		}
		return timeDifference(firstTracepoint,lastTracePoint);
	}
	
	// in this format :- HH:mm:ss.SSSSSSSSS
	public static double timeDifference(String startTime, String endTime){
		int startHour = Integer.parseInt(startTime.substring(0,2));
		int startMin = Integer.parseInt(startTime.substring(3,5));
		int startSec = Integer.parseInt(startTime.substring(6,8));
		double startMillisec = Double.parseDouble("0"+startTime.substring(8,startTime.length()));
		int endHour = Integer.parseInt(endTime.substring(0,2));
		int endMin = Integer.parseInt(endTime.substring(3,5));
		int endSec = Integer.parseInt(endTime.substring(6,8));
		double endMillisec = Double.parseDouble("0"+endTime.substring(8,endTime.length()));
		
		return (endHour*3600+endMin*60+endSec+endMillisec) - (startHour*3600+startMin*60+startSec+startMillisec);
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// Scan all the methods listed in the log file
	private void scanMethods(Scanner sc) {
		Pattern pat = Pattern.compile("J9 timer[(]UTC[)].*");
		String nextLine;
		Matcher m;
		boolean found = false;
		while(sc.hasNext()){
			nextLine = sc.nextLine();
			m = pat.matcher(nextLine);
			if(m.matches()){
				found = true;
				break;
			}
		}
		String timePattern = "([0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{9})"; // group 1 :- Contains time in this format 19:04:24.092291382
		String threadIdPattern = "[\\*]?(0x[a-fA-F0-9]+)";	// group 2 :- Contains threadID in this format *0x26e0500
		String methodTraceIdPattern = "(mt\\.[0-9]+)"; 	// group 3 :- Contains trace id in this format mt.3
		String typePattern = "(Entry|Exit)";	// group 4 :- Contains Entry or Exit
		// group 5 :- java/util/Scanner  		(ClassName)
		// group 6 :- <clinit>			 		(MethodName)
		// group 7 :- (Ljava/lang/Readable;)	(Parameters)
		// group 8 :- Ljava/util/Scanner;		(ReturnType)
		// group 9 :- static method      		(Static or not)
		String traceEntryPattern = "[><](.+)\\.([a-zA-Z0-9<>]+)\\((.+)?\\)([\\S]+)\\s(.*)"; 	
		pat = Pattern.compile(timePattern+"\\s+"+threadIdPattern+"\\s+"+methodTraceIdPattern+"\\s+"+typePattern+"\\s+"+traceEntryPattern);
		String methodName;
		String className;
		String threadId;
		String startTime;
		String parameters[];
		String returnType;
		String thisPointer;
		//System.out.println(timePattern+"\\s+"+threadIdPattern+"\\s+"+methodTraceIdPattern+"\\s+"+typePattern+"\\s+"+traceEntryPattern);
		boolean staticOrNot;	// Set True if method is static
		Stack<Method> methods = new Stack<>();
		Method method;
		while(sc.hasNext()){
			nextLine = sc.nextLine();
			m = pat.matcher(nextLine);
			if(m.matches()){
				if(m.group(4).equals("Entry")){
					startTime = m.group(1);
					className = m.group(5);
					threadId = m.group(2);
					methodName = m.group(6);
					parameters = getParameters(m.group(7));
					returnType = getReturnType(m.group(8));
					
					if(m.group(9).contains("static")){
						staticOrNot = true;
						method = new Method(methodName, className, threadId, startTime, staticOrNot, parameters, returnType);
						methods.push(method);
					}
					else{
						staticOrNot =false;
						Pattern thisPointerPattern = Pattern.compile(".*(0x[0-9a-fA-F]+)");
						m = thisPointerPattern.matcher(m.group(9));
						if(m.matches()){
							thisPointer = m.group(1);
							method = new Method(methodName, className, threadId, startTime, staticOrNot, thisPointer, parameters, returnType);
							methods.push(method);
						}
					}
				}
				else{
					method = methods.pop();
					method.setEndTime(m.group(1)); 
					activeThreads.get(m.group(2)).addMethod(method);
				}
			}
			else{
				break;
			}
		}
		// Check if stack is empty, if it is not empty that means some methods that started has not exited, Next line of codes
		// handles that situation
		if(!methods.empty()){
			for(Method met: methods){
				activeThreads.get(met.getThreadId()).addMethod(met);
			}
		}
		// Calculate runtime of all the methods in all the thread
		for(Threads th: activeThreads.values()){
			for(Method met: th.getMethods()){
					met.calculateRuntime();
			}
		}
	}

	public String[] getParameters(String s){
		String[] params = {"void"};
		if(!(s == null)){
			params = s.split(";");
			return params;
		}
		return params;
	}
	private String getReturnType(String s) {
		if(s.equals("V")){
			return "void";
		}
		else{
			Pattern p = Pattern.compile("[A-Z](.*);");
			Matcher m = p.matcher(s);
			if(m.matches()){
				return m.group(1);
			}
		}
		return "void";
	}
	
	public HashMap<String, Threads> getActiveThreads(){
		return activeThreads;
	}
	public double getTraceTime(){
		return traceTime;
	}
	public Method getMethodById(int id){
		for(Threads th: activeThreads.values()){
			for(Method met: th.getMethods()){
				if(met.getId() == id){
					return met;
				}
			}
		}
		return null;

	}
	
	
	// Get all the methods and sort them by their runtime
	public ArrayList<Method> sortByRuntime(){
		List<Method> runtimes = new ArrayList<>();
		for(Threads th: activeThreads.values()){
			for(Method met: th.getMethods()){
				if(met.hasEnded()){
					runtimes.add(met);
				}
			}
		}
		Collections.sort(runtimes, new Parser.CustomComparator());
		return (ArrayList<Method>) runtimes;
	}
	// Comparator to sort
	static class CustomComparator implements Comparator<Method> {
	    @Override
	    public int compare(Method m1, Method m2) {
	    	double m1Runtime = m1.getRuntime();
	        double m2Runtime = m2.getRuntime();
	        // uses compareTo method of String class to compare names of the employee
	        return Double.compare(m1Runtime, m2Runtime);
	    }
	}
	
 }
	












