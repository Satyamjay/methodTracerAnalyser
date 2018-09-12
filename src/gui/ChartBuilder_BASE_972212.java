package gui;

import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import parser.Method;
import parser.Threads;
import parser.Parser;
public class ChartBuilder {
	private JFreeChart chart;
	public ChartBuilder(Parser p){
		
	  final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	  HashMap<String, Threads> activeThreads = p.getActiveThreads();
	  for(Threads th: activeThreads.values()){
			System.out.println(th);
			for(Method met: th.getMethods()){
				if(met.hasEnded()){
				dataset.addValue( met.getRuntime() , met.getMethodName() , "" );        
				}
			}
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
