package parser;

import java.util.ArrayList;
import java.util.List;

public class LogComparator {
	private List<Method> criticalMethodsInP1;									// Contains all the methods that took most of the time and the incomplete methods
	private List<Method> criticalMethodsInP2;								    // Same as above
	private List<CommonMethods> commonCriticalMethodsInP1 = new ArrayList<>();	// Methods that are critical in P1 and also present in P2
	private List<CommonMethods> commonCriticalMethodsInP2 = new ArrayList<>();	// Same as above

	public LogComparator(Parser p1, Parser p2) {
		
		criticalMethodsInP1 = new ArrayList<>();
		criticalMethodsInP1.addAll(p1.getIncompleteMethods());
		List<Method> super30MethodsInP1 = p1.sortByRuntime();
		if(super30MethodsInP1.size()>=30){
			super30MethodsInP1 = super30MethodsInP1.subList(0, 30);
		}
		criticalMethodsInP1.addAll(super30MethodsInP1);
		
		criticalMethodsInP2 = new ArrayList<>();
		criticalMethodsInP2.addAll(p2.getIncompleteMethods());
		List<Method> super30MethodsInP2 = p2.sortByRuntime();
		if(super30MethodsInP2.size()>=30){
			super30MethodsInP2 = super30MethodsInP2.subList(0, 30);
		}
		criticalMethodsInP2.addAll(super30MethodsInP2);
		
		// Populating commonCriticalMethods
		for(Method met:criticalMethodsInP1){
			for(Method mt:p2.getAllMethods()){
				if(met.equals(mt)){
					commonCriticalMethodsInP1.add(new CommonMethods(met.getMethodName(), met.getClassName(), met.isStaticOrNot(), met.getParameters(), met.getReturnType(), met.getRuntime(), mt.getRuntime(), met.getMethodStack(), mt.getMethodStack()));
					break;
				}
			}
		}
		for(Method met:criticalMethodsInP2){
			for(Method mt:p1.getAllMethods()){
				if(met.equals(mt)){
					commonCriticalMethodsInP2.add(new CommonMethods(met.getMethodName(), met.getClassName(), met.isStaticOrNot(), met.getParameters(), met.getReturnType(), met.getRuntime(), mt.getRuntime(), met.getMethodStack(), mt.getMethodStack()));
					break;
				}
			}
		}	}
	
	// Gives the array of CommonMethods of size two
	public List<CommonMethods>[] getCommonCriticalMethods(){
		List<CommonMethods>[] commonCriticalMethods = new List[2];
		commonCriticalMethods[0] = commonCriticalMethodsInP1;
		commonCriticalMethods[1] = commonCriticalMethodsInP2;
		return commonCriticalMethods;
	}

}
