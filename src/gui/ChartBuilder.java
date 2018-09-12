package gui;

import java.util.HashMap;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import parser.Threads;





import parser.Method;
import parser.Parser;
public class ChartBuilder {
	private JFreeChart chart;
	DefaultTableModel model = new DefaultTableModel();
	 JTable table = new JTable(model);
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
