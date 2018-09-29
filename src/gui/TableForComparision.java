package gui;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import parser.CommonMethods;


public class TableForComparision {
	private List<CommonMethods> commonCriticalMethodsinP1 = new ArrayList<>();
	private List<CommonMethods> commonCriticalMethodsinP2 = new ArrayList<>();
	
	public TableForComparision(List<CommonMethods>[] commonCriticalMethods) {
		commonCriticalMethodsinP1 = commonCriticalMethods[0];
		commonCriticalMethodsinP2 = commonCriticalMethods[1];
	}
	
	public JTable[] createTablesForComparision(){
		// For Table 1
		DefaultTableModel model1 = new DefaultTableModel();
		final JTable table1 = new JTable(model1);
	    model1.addColumn("Method");
	    model1.addColumn("Class Name");
	    model1.addColumn("Static Or Not");
	    model1.addColumn("Parameter");
	    model1.addColumn("Return Type");
	    model1.addColumn("Runtime in first file");
	    model1.addColumn("Runtime in second file");
	    model1.addColumn("Stack Trace in first file");
	    model1.addColumn("StackTrace in second file");
	    for(CommonMethods met: commonCriticalMethodsinP1){
			  model1.addRow(new Object[] {met.getMethodName(), met.getClass(), met.isStaticOrNot(), met.getParameters(), met.getReturnType(), met.getRunTime1(), met.getRuntime2(), "Click for StackTrace", "Click for StackTrace"}); 
		  }
	    table1.setFont(new Font("Serif", Font.PLAIN, 20));
		table1.setRowHeight(40);
		table1.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
		table1.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer());
		// For Table 2
		DefaultTableModel model2 = new DefaultTableModel();
		final JTable table2 = new JTable(model2);
	    model2.addColumn("Method");
	    model2.addColumn("Class Name");
	    model2.addColumn("Static Or Not");
	    model2.addColumn("Parameter");
	    model2.addColumn("Return Type");
	    model2.addColumn("Runtime in second file");
	    model2.addColumn("Runtime in first file");
	    model2.addColumn("Stack Trace in second file");
	    model2.addColumn("StackTrace in first file");
	    for(CommonMethods met: commonCriticalMethodsinP2){
			  model2.addRow(new Object[] {met.getMethodName(), met.getClass(), met.isStaticOrNot(), met.getParameters(), met.getReturnType(), met.getRunTime1(), met.getRuntime2(), "Click for StackTrace", "Click for StackTrace"}); 
		  }
	    table2.setFont(new Font("Serif", Font.PLAIN, 20));
		table2.setRowHeight(40);
		table2.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
		table2.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer());
		
		
		JTable[] tables = {table1, table2};
		return tables;
	}
	
	
	
	
}
