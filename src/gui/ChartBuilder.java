package gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartBuilder {
	private JFreeChart chart;
	public ChartBuilder(){
		
	  final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	  dataset.addValue( 1.0 , "method1" , "" );        
      dataset.addValue( 5.0 , "method2" , "" );        
      dataset.addValue( 4.0 , "method3" , "" );         
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
