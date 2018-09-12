package gui;

import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
public class TableBuilder {
	private JFreeChart chart;
	//To create Table in the JFrame
	DefaultTableModel model = new DefaultTableModel();
	JTable table = new JTable(model);
	private int serialNo=1;
	public TableBuilder(Parser p){
		
	  final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	  HashMap<String, Threads> activeThreads = p.getActiveThreads();
	  model.addColumn("SNo");					//Adding columns to the JTable
	  model.addColumn("Runtime");
	  model.addColumn("Method");
	  for(Threads th: activeThreads.values()){
			System.out.println(th);
			for(Method met: th.getMethods()){
				if(met.hasEnded()){
						//Adding rows Dynamically to the JTable
					    model.addRow(new Object[] {serialNo, met.getRuntime(),met.getMethodName()}); 
					    serialNo++;
				}
			}
		}
   
}
	public JTable getTable(){
		return table;
	}
	
	}
