package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import parser.Method;
import parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;


// This jFrame is used to show the graphs using the ChartBuilder class
public class FrameForChart extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 * Create the frame.
	 */
	public FrameForChart(final Parser p) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		ChartBuilder chart = new ChartBuilder(p);
		ChartPanel cp = new ChartPanel(chart.getChart());
		cp.addChartMouseListener(new ChartMouseListener() {
			
			@Override
			public void chartMouseMoved(ChartMouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void chartMouseClicked(ChartMouseEvent e) {
				Pattern pat = Pattern.compile(".+\\s\\(([0-9]+)\\),.+");
				Matcher mat = pat.matcher(e.getEntity().toString());
				if(mat.matches()){
					final Method met = p.getMethodById(Integer.parseInt((mat.group(1))));
					String[] columns = new String[]{
				            "MethodName", "ClassName", "Static", "StartTime", "EndTime", "TotalRuntime", "Parameters", "ReturnType", "StackTrace"
					};
				    Object[][] data = new Object[][] {{met.getMethodName(), met.getClass(), met.isStaticOrNot(), met.getStartTime(), met.getEndTime(), met.getRuntime(), met.getParameters(), met.getReturnType(), "StackTrace"}};
			        final JTable table = new JTable(data, columns);
			        table.setFont(new Font("Serif", Font.PLAIN, 20));
			        table.setRowHeight(40);
			  	    table.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer());
			  	    table.addMouseListener(new java.awt.event.MouseAdapter() {
					    @Override
					    public void mouseClicked(java.awt.event.MouseEvent evt) {
					        int row = table.rowAtPoint(evt.getPoint());
					        int col = table.columnAtPoint(evt.getPoint());
					        if (col==8) {
					        	JDialog d = new JDialog();
					        	d.setSize(500, 1000);
					            d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					        	try{
						        	List<String> methodStackList = new ArrayList<String>(met.getMethodStack());
					        		d.setTitle(met.getMethodName()+" Stack Trace");
						            DefaultTableModel model = new DefaultTableModel();
						        	JTable t = new JTable(model);
						      	    model.addColumn("<html><font size=8>"+"StackTrace"+"</font></html>");
						      	    d.setTitle("StackTrace For "+met.getMethodName());
						      	    for(String method: methodStackList){
						    		  model.addRow(new Object[] {method});
						    	    }
						            d.add(t);
						            d.setVisible(true);
					        	}
					        	catch(NullPointerException ex){
					        		d.setVisible(true);
					        	}
					        }
					    }
					});
					JFrame frameForMethodInfo = new JFrame();
					frameForMethodInfo.setPreferredSize(new Dimension(1000, 150));
					frameForMethodInfo.add(new JScrollPane(table));
			        frameForMethodInfo.setTitle(met.getMethodName());
			        frameForMethodInfo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);       
			        frameForMethodInfo.pack();
			        frameForMethodInfo.setVisible(true);   
				}
			}
		});
		contentPane.setLayout(new java.awt.BorderLayout());
		contentPane.add(cp, BorderLayout.CENTER);
		setContentPane(contentPane);
		
	}
}

// Class used to render the button in the jtable
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
