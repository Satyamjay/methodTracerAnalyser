package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;	

import parser.Method;
import parser.Parser;
public class TableBuilder {
	//To create Table in the JFrame
	DefaultTableModel model = new DefaultTableModel();
	private JTable table = new JTable(model);
	private int serialNo=1;
	@SuppressWarnings("serial")
	public TableBuilder(Parser p){
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