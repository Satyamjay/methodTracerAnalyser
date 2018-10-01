# A Program to view logs interactively.

## How to run the program
To run the program use :-

```
java -jar -Xms1024m MethodTraceAnalyser.jar         (Larger files require more memory and give outOfHeapMemory error)

```
To test the program you will need the trace files.
Some of the trace files are included but those files do not contain stacktrace as the files get more than 200 mb when stacktrace is included in them.

## About the project
Performs two basic tasks:-
1. Parses the trace file then shows the table and a chart.
2. If user wants allow him/her to compare the trace with another trace file.
<img src="https://github.com/Satyamjay/methodTracerAnalyser/blob/master/FlowCharts/flowChart.PNG" width="50%"/>

## Acknowledgment

* JFreeChart OpenSource libraries were used to create charts in the project. http://www.jfree.org/jfreechart/
