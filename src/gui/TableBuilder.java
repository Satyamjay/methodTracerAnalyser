package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;	
import javax.swing.table.TableCellRenderer;

import parser.Method;
import parser.Parser;
public class TableBuilder {
	//To create Table in the JFrame
	DefaultTableModel model = new DefaultTableModel();
	private JTable table = new JTable(model);
	private int serialNo=1;
	
	@SuppressWarnings("serial")
	public TableBuilder(final Parser p){
	  model.addColumn("<html><font size=8>"+"SNo"+"</font></html>");					//Adding columns to the JTable
	  model.addColumn("Runtime");
	  model.addColumn("Method");
	  model.addColumn("Class Name");
	  model.addColumn("Static Or Not");
	  model.addColumn("Start Time");
	  model.addColumn("End Time");
	  model.addColumn("Parameter");
	  model.addColumn("Return Type");
	  model.addColumn("Stack Trace");
	 
	  for(Method met: p.getIncompleteMethods()){
		  model.addRow(new Object[] {serialNo, met.getRuntime(),met.getMethodName(), met.getClass(), met.isStaticOrNot(), met.getStartTime(), met.getEndTime(), met.getParameters(), met.getReturnType(), "Click to see stackTrace"}); 
		  serialNo++;
	  }
	  for(Method met: p.sortByRuntime()){
		  model.addRow(new Object[] {serialNo, met.getRuntime(),met.getMethodName(), met.getClass(), met.isStaticOrNot(), met.getStartTime(), met.getEndTime(), met.getParameters(), met.getReturnType(), "Click to see stackTrace"}); 
		  serialNo++;
	  }
	  table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
		  @Override
          public Component getTableCellRendererComponent(JTable table,
                  Object value, boolean isSelected, boolean hasFocus, int row, int col) {
              super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
              Double status = (Double) table.getModel().getValueAt(row, 1);
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
	  table.setFont(new Font("Serif", Font.PLAIN, 20));
	  table.setRowHeight(40);
	  table.getColumnModel().getColumn(9).setCellRenderer(new ButtonRenderer());
	  table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (col==9) {
		        	Method m = p.getMethodById(row);
		        	JDialog d = new JDialog();
		        	d.setSize(500, 1000);
		            d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		        	try{
			        	List<String> methodStackList = new ArrayList<String>(m.getMethodStack());
		        		System.out.println(m.getMethodStack());
		        		d.setTitle(m.getMethodName()+" Stack Trace");
			            DefaultTableModel model = new DefaultTableModel();
			        	JTable t = new JTable(model);
			      	    model.addColumn("<html><font size=8>"+"StackTrace"+"</font></html>");
			      	    for(String method: methodStackList){
			    		  model.addRow(new Object[] {method});
			    	    }
			            d.add(t);
			            d.setVisible(true);
		        	}
		        	catch(NullPointerException ex){
		        		System.out.println("Here");
		        		d.add(new JLabel("StackTrace not available for this method in the log file"));
		        		d.setVisible(true);
		        	}
		        }
		    }
		});
}	
	public JTable getTable(){
		return table;
	}
}

class ButtonRenderer extends JButton implements TableCellRenderer{
	
	public ButtonRenderer() {
		
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setText((value==null) ? "":value.toString());
		return this;
	}
}