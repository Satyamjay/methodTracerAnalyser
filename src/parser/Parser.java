/**
 * 
 */
package parser;
import parser.Method;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	private HashMap<String, Threads> activeThreads;
	// Contains start-time and end-time of the trace
	private double traceTime; // in seconds 
	public Parser(File fileName){
		Scanner sc = null;
		try{
			sc = new Scanner(fileName); 
			activeThreads = getThreadInfo(sc);
			traceTime = getTraceTime(sc);
			scanMethods(sc);
			System.out.println(traceTime);
		}
		catch(IOException e){
			System.out.println("File Not Found");
		}
	}


	private HashMap<String, Threads> getThreadInfo(Scanner sc){
		boolean found = false;
		Pattern pat = Pattern.compile("\\s{8}(0x[a-fA-F0-9]+)\\s{2}([a-fA-Z0-9]+)"); // Pattern example:- [        0x26e0500  main]
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
				if(nextLine.equals("")){
					break;
				}
			}
			return threadInfoList;
		}
		return null;
	}
	
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
				System.out.println("found");
				break;
			}
		}
		String timePattern = "([0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{9})";
		String threadIdPattern = "[\\*]?(0x[a-fA-F0-9]+)";
		String methodTraceIdPattern = "(mt\\.[0-9]+)";
		String typePattern = "(Entry|Exit)";
		String traceEntryPattern = "([><].*)";
		pat = Pattern.compile(timePattern+"\\s+"+threadIdPattern+"\\s+"+methodTraceIdPattern+"\\s+"+typePattern+"\\s+"+traceEntryPattern);
		String methodName;
		String className;
		String startTime;
		String endTime;
		boolean staticOrNot;	// Set True if method is static
		String thisPointer;
		while(sc.hasNext()){
			nextLine = sc.nextLine();
			m = pat.matcher(nextLine);
			if(m.matches()){
				if(m.group(4).equals("Entry")){
					
				}
			}
			else{
				break;
			}
		}
		
	}
	
	
	
	
	
	
	
	
 }
	










