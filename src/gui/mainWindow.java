package gui;

import java.awt.EventQueue;

import parser.*;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import gui.FrameForResult;
import gui.TableForComparision;

public class mainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 **/
	public static void main(String[] args) {
		//File fileName = new File("demoLogs/demoMultiThread.log");
		//Parser p = new Parser(fileName);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow window = new mainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnFileselect = new JButton("Select log file to analyze");
		btnFileselect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					JFileChooser jfc1 = new JFileChooser();
					int returnVal1 = jfc1.showOpenDialog(frame);
					if(returnVal1 == JFileChooser.APPROVE_OPTION) {
						Parser p1 = new Parser(jfc1.getSelectedFile());
						int result = JOptionPane.showConfirmDialog((Component) null, "Wanna Compare with another log?","alert", JOptionPane.YES_NO_OPTION);
						if( result == JOptionPane.YES_OPTION){
							JFileChooser jfc2 = new JFileChooser();
							int returnVal2 = jfc2.showOpenDialog(frame);
							if(returnVal2 == JFileChooser.APPROVE_OPTION){
								Parser p2 = new Parser(jfc2.getSelectedFile());
								showTable(p1);
								showTable(p2);
								showComparisionTable(new LogComparator(p1, p2));
							}
						}
						else{
							showTable(p1);
						}
					}
				}
				catch(InvalidLogFileException ilfe){
					JOptionPane.showMessageDialog(frame, "Invalid Log File");
				}
			}
		});
		frame.getContentPane().add(btnFileselect, BorderLayout.NORTH);
	}
	private void showTable(Parser p){
		final FrameForResult frameForResult = new FrameForResult(p);
		final GraphViewer graphViewer = new GraphViewer(p);
		graphViewer.setDefaultCloseOperation(GraphViewer.DISPOSE_ON_CLOSE);
		frameForResult.setDefaultCloseOperation(FrameForResult.DISPOSE_ON_CLOSE);
		frameForResult.setVisible(true);
		graphViewer.setVisible(true);
	}
	private void showComparisionTable(LogComparator lc){
		List<CommonMethods>[] cm = lc.getCommonCriticalMethods();
		JTable[] tables = new TableForComparision(cm).createTablesForComparision();
		JTable table1 = tables[0];
		JTable table2 = tables[1];
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = frame.getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));;
		c.add(table1.getTableHeader());
		c.add(table1);
		c.add(table2.getTableHeader());
		c.add(table2);
		frame.pack();
		frame.setVisible(true);
		
	}

}
