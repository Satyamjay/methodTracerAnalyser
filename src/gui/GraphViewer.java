package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import parser.Method;
import parser.Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;

public class GraphViewer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 * Create the frame.
	 */
	public GraphViewer(final Parser p) {
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
					Method met = p.getMethodById(Integer.parseInt((mat.group(1))));
					String[] columns = new String[]{
				            "MethodName", "ClassName", "Static", "StartTime", "EndTime", "TotalRuntime", "Parameters", "ReturnType", "StackTrace"
					};
				    //actual data for the table in a 2d array
				    Object[][] data = new Object[][] {{met.getMethodName(), met.getClass(), met.isStaticOrNot(), met.getStartTime(), met.getEndTime(), met.getRuntime(), met.getParameters(), met.getReturnType(), met.getMethodStack()}};
			        JTable table = new JTable(data, columns);
					JFrame frameForMethodInfo = new JFrame();
					frameForMethodInfo.setPreferredSize(new Dimension(1000, 150));
					frameForMethodInfo.add(new JScrollPane(table));
			        frameForMethodInfo.setTitle("Table Example");
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
