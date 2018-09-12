package gui;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
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
	private JTable table = new JTable(model);
	private int serialNo=1;
	public TableBuilder(Parser p){
	  final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	  HashMap<String, Threads> activeThreads = p.getActiveThreads();
	  model.addColumn("SNo");					//Adding columns to the JTable
	  model.addColumn("Runtime");
	  model.addColumn("Method");
	  for(Method met: p.getIncompleteMethods()){
		  model.addRow(new Object[] {serialNo, met.getRuntime(),met.getMethodName()}); 
		  serialNo++;
	  }
	  for(Method met: p.sortByRuntime()){
		  model.addRow(new Object[] {serialNo, met.getRuntime(),met.getMethodName()}); 
		  serialNo++;
	  }
	  table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
		  @Override
          public Component getTableCellRendererComponent(JTable table,
                  Object value, boolean isSelected, boolean hasFocus, int row, int col) {
              super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
              Double status = (Double)table.getModel().getValueAt(row, 1);
              if (status == -1) {
                  setBackground(Color.RED);
                  setForeground(Color.WHITE);
              } else {
                  setBackground(table.getBackground());
                  setForeground(table.getForeground());
              }
			return this;       
          }   
      });	  
}
	
	
	public JTable getTable(){
		return table;
	}
	
}