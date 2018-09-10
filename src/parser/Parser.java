/**
 * 
 */
package parser;
import parser.Method;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.Threads;

import java.util.HashMap;

import javax.swing.text.StyledEditorKit.ForegroundAction;
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
	public Parser(File fileName){
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
	private HashMap<String, Threads> getThreadInfo(Scanner sc){
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
		return null;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	// Get trace time according to log file
	private double getTraceTime(Scanner sc){
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
		return timeDifference(firstTracepoint,lastTracePoint);
	}
	
	// in this format :- HH:mm:ss.SSSSSSSSS
	private double timeDifference(String startTime, String endTime){
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
		// group 5 :- java/util/Scanner  (ClassName)
		// group 6 :- <clinit>			 (MethodName)
		// group 7 :- ()                 (Parameters)
		// group 8 :- static method      (Static or not)
		String traceEntryPattern = "[><]([a-zA-Z0-9]+)\\.([a-zA-Z0-9<>]+)\\((.+)?\\)[A-Z\\]]\\s(.*)"; 	
		pat = Pattern.compile(timePattern+"\\s+"+threadIdPattern+"\\s+"+methodTraceIdPattern+"\\s+"+typePattern+"\\s+"+traceEntryPattern);
		String methodName;
		String className;
		String threadId;
		String startTime;
		String endTime;
		String thisPointer;
		//System.out.print(timePattern+"\\s+"+threadIdPattern+"\\s+"+methodTraceIdPattern+"\\s+"+typePattern+"\\s+"+traceEntryPattern);
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
					if(m.group(8).contains("static")){
						staticOrNot = true;
						method = new Method(methodName, className, threadId, startTime, staticOrNot);
						methods.push(method);
					}
					else{
						staticOrNot =false;
						Pattern thisPointerPattern = Pattern.compile(".*(0x[0-9a-fA-F]+)$");
						m = thisPointerPattern.matcher(m.group(8));
						if(m.matches()){
							thisPointer = m.group(1);
							method = new Method(methodName, className, threadId, startTime, staticOrNot, thisPointer);
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
	}
	
	public HashMap<String, Threads> getActiveThreads(){
		return activeThreads;
	}
	public double getTraceTime(){
		return traceTime;
	}
	
 }
	










