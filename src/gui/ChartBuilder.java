package gui;

import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;






import parser.Method;
import parser.Parser;

// This class creates the chart to be shown on the GraphViewerFrame
public class ChartBuilder {
	private JFreeChart chart;
	public ChartBuilder(Parser p){
	  final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	  List<Method> methods = p.sortByRuntime();
	  if(methods.size()>=30){
		  methods = methods.subList(0, 30);
	  }
	  for(Method met: methods){
		  dataset.addValue( met.getRuntime() , met.getMethodName()+" ("+met.getId()+")" , "" );        
	  }
	  chart = ChartFactory.createBarChart(
			   "Method Runtime Comparision", // Title
			   "Methods", // x-axis Label
			   "Run Time", // y-axis Label
			   dataset, // Dataset
			   PlotOrientation.VERTICAL, // Plot Orientation
			   true, // Show Legend
			   true, // Use tooltips
			   false // Configure chart to generate URLs?
			);
	}
		
	public JFreeChart getChart(){
		return chart;
	}
}
