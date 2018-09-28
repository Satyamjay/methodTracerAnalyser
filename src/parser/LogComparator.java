package parser;

import java.util.ArrayList;
import java.util.List;

public class LogComparator {
	List<Method> criticalMethodsInP1;
	List<Method> criticalMethodsInP2;
	List<CommonMethods> commonCriticalMethodsInP1 = new ArrayList<>();
	List<CommonMethods> commonCriticalMethodsInP2 = new ArrayList<>();

	public LogComparator(Parser p1, Parser p2) {
		criticalMethodsInP1 = new ArrayList<>();
		List<Method> super30Methods = p1.sortByRuntime();
		if(super30Methods.size()>=30){
			super30Methods = super30Methods.subList(0, 30);
		}
		criticalMethodsInP1.addAll(super30Methods);
		criticalMethodsInP1.addAll(p1.getIncompleteMethods());
		
		criticalMethodsInP2 = new ArrayList<>();
		List<Method> super30MethodsInP2 = p2.sortByRuntime();
		if(super30MethodsInP2.size()>=30){
			super30MethodsInP2 = super30MethodsInP2.subList(0, 30);
		}
		criticalMethodsInP2.addAll(super30Methods);
		criticalMethodsInP2.addAll(p2.getIncompleteMethods());
		
		for(Method mt:criticalMethodsInP1){
			for(Method met:p2.getAllMethods()){
				if(met.equals(mt)){
					commonCriticalMethodsInP1.add(new CommonMethods(met.getMethodName(), met.getClassName(), met.isStaticOrNot(), met.getParameters(), met.getReturnType(), met.getRuntime(), mt.getRuntime(), met.getMethodStack(), mt.getMethodStack()));
				}
			}
		}
		for(Method mt:criticalMethodsInP2){
			for(Method met:p1.getAllMethods()){
				if(met.equals(mt)){
					commonCriticalMethodsInP2.add(new CommonMethods(met.getMethodName(), met.getClassName(), met.isStaticOrNot(), met.getParameters(), met.getReturnType(), met.getRuntime(), mt.getRuntime(), met.getMethodStack(), mt.getMethodStack()));
				}
			}
		}	}
	
	public List<CommonMethods>[] getCommonCriticalMethods(){
		List<CommonMethods>[] commonCriticalMethods = new List[2];
		commonCriticalMethods[0] = commonCriticalMethodsInP1;
		commonCriticalMethods[1] = commonCriticalMethodsInP2;
		return commonCriticalMethods;
	}

}
