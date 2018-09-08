/**
 * 
 */
package parser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.ThreadInfo;
/**
 * @author satyam
 *
 */
public class Parser{
	
	private List<ThreadInfo> activeThreads;
	public Parser(){
		Scanner sc = null;
		try{
			File fileName = new File("demoLogs/demoAvg.log");
			sc = new Scanner(fileName); 
			activeThreads = getThreadInfo(sc);
			System.out.println(activeThreads.get(0).getThreadID());
		}
		catch(IOException e){
			System.out.println("File Not Found");
		}
	}
	
	private List<ThreadInfo> getThreadInfo(Scanner sc){
		boolean found = false;
		Pattern pat = Pattern.compile("\\s{8}(0x[a-zA-A0-9]+)\\s{2}([a-zA-A0-9]+)");
		while(sc.hasNextLine()){
			found = Pattern.matches("Active Threads :", sc.nextLine());
			if(found){
				break;
			}
		}
		if(found){
			List<ThreadInfo> threadInfoList = new ArrayList<>();
			while(sc.hasNextLine()){
				String nextLine = sc.nextLine();
				Matcher m = pat.matcher(nextLine);
				if(m.matches()){
					threadInfoList.add(new ThreadInfo(m.group(1),m.group(2)));
				}
				
			}
			return threadInfoList;
		}
		return null;
		
		
	}
	
	
	
	
	
	
	
 }
	










