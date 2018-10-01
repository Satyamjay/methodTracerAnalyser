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
<img src="https://mermaidjs.github.io/mermaid-live-editor/#/view/eyJjb2RlIjoiZ3JhcGggVERcbkFbVXNlcl0gLS0-fEZpbGV8IEIoUGFyc2VyKVxuQiAtLT4gfFBhcnNlciBPYmplY3R8IEMoVGFibGVCdWlsZGVyKVxuQiAtLT4gfFBhcnNlciBPYmplY3R8IEYoQ2hhcnRCdWlsZGVyKVxuQyAtLT4gfGpUYWJsZSBPYmplY3R8IERbRnJhbWVGb3JSZXN1bHRdXG5GIC0tPiB8akZyZWVDaGFydCBPYmplY3R8IEcoRnJhbWVGb3JDaGFydClcbkcgLS0-IHxqRnJhbWV8IEVcbkQgLS0-fGpGcmFtZXwgRVttYWluV2luZG93XVxuIiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifX0" width="20%"/>

## Acknowledgment

* JFreeChart OpenSource libraries were used to create charts in the project. http://www.jfree.org/jfreechart/
