package parser;

import java.util.Stack;

public class CommonMethods{
	private String methodName;
	private String className;
	private String parameters[];
	private String returnType;
	private boolean staticOrNot;
	private double runTime1;
	private double runtime2;
	private Stack<String> methodStack1 = new Stack<>();
	private Stack<String> methodStack2 = new Stack<>();
	
	public CommonMethods(String mName, String cName, boolean sOrNot, String[] params, String rType, double rt1, double rt2, Stack<String> ms1, Stack<String> ms2){
		this.setMethodName(mName);
		this.setClassName(cName);
		this.setParameters(params);
		this.setReturnType(rType);
		this.setStaticOrNot(sOrNot);
		this.setRunTime1(rt1);
		this.setRuntime2(rt2);
		this.setMethodStack1(ms1);
		this.setMethodStack2(ms2);
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String[] getParameters() {
		return parameters;
	}

	public void setParameters(String parameters[]) {
		this.parameters = parameters;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public boolean isStaticOrNot() {
		return staticOrNot;
	}

	public void setStaticOrNot(boolean staticOrNot) {
		this.staticOrNot = staticOrNot;
	}

	public double getRunTime1() {
		return runTime1;
	}

	public void setRunTime1(double runTime1) {
		this.runTime1 = runTime1;
	}

	public double getRuntime2() {
		return runtime2;
	}

	public void setRuntime2(double runtime2) {
		this.runtime2 = runtime2;
	}

	public Stack<String> getMethodStack1() {
		return methodStack1;
	}

	public void setMethodStack1(Stack<String> methodStack1) {
		this.methodStack1 = methodStack1;
	}

	public Stack<String> getMethodStack2() {
		return methodStack2;
	}

	public void setMethodStack2(Stack<String> methodStack2) {
		this.methodStack2 = methodStack2;
	}
}
