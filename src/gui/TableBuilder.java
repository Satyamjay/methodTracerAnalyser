package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.plaf.FontUIResource;
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
	  model.addColumn("<html><font size=8>"+"SNo"+"</font></html>");		//Adding columns to the JTable
	  model.addColumn("<html><font size=8>"+"Runtime"+"</font></html>");
	  model.addColumn("<html><font size=8>"+"Method"+"</font></html>");
	  for(Method met: p.getIncompleteMethods()){
		  model.addRow(new Object[] {"<html><font size=6>"+serialNo+"</font></html>", "<html><font size=6>"+String.valueOf(met.getRuntime())+"</font></html>","<html><font size=6>"+met.getMethodName()+"</font></html>"}); 
		  serialNo++;
	  }
	  for(Method met: p.sortByRuntime()){
		  model.addRow(new Object[] {"<html><font size=6>"+serialNo+"</font></html>", "<html><font size=6>"+String.valueOf(met.getRuntime())+"</font></html>","<html><font size=6>"+met.getMethodName()+"</font></html>"}); 
		  serialNo++;
	  }
	  // To show the incomplete methods in red
	  table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
		  @Override
          public Component getTableCellRendererComponent(JTable table,
                  Object value, boolean isSelected, boolean hasFocus, int row, int col) {
              super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
              String status = (String) table.getModel().getValueAt(row, 1);
              if (status == "-1") {
                  setBackground(Color.RED);
                  setForeground(Color.WHITE);
              } else {
                  setBackground(table.getBackground());
                  setForeground(table.getForeground());
              }
			return this;       
          }   
      });
	  table.setRowHeight(40);
}
	
	
	public JTable getTable(){
		return table;
	}
	
}