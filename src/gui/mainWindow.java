package gui;

import java.awt.EventQueue;

import parser.*;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import gui.FrameForResult;
import gui.TableForComparision;

public class mainWindow {

	private JFrame frame;
	private Parser p1;
	private Parser p2;
	private LogComparator logComparator;

	

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
					final JFileChooser jfc1 = new JFileChooser();
					int returnVal1 = jfc1.showOpenDialog(frame);
					if(returnVal1 == JFileChooser.APPROVE_OPTION) {
						parseWithProgressBar(jfc1.getSelectedFile(), 1);
						int result = JOptionPane.showConfirmDialog((Component) null, "Wanna Compare with another log?","alert", JOptionPane.YES_NO_OPTION);
						if( result == JOptionPane.YES_OPTION){
							JFileChooser jfc2 = new JFileChooser();
							int returnVal2 = jfc2.showOpenDialog(frame);
							if(returnVal2 == JFileChooser.APPROVE_OPTION){
								parseWithProgressBar(jfc2.getSelectedFile(), 2);
								showTable(p1, jfc1.getSelectedFile().getName());
								showTable(p2, jfc2.getSelectedFile().getName());
								compareWithProgressBar(p1, p2);
								showComparisionTable(logComparator);
							}
						}
						else{
							showTable(p1, jfc1.getSelectedFile().getName());
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
	private void showTable(Parser p, String fileName){
		final FrameForResult frameForResult = new FrameForResult(p);
		final GraphViewer graphViewer = new GraphViewer(p);
		graphViewer.setDefaultCloseOperation(GraphViewer.DISPOSE_ON_CLOSE);
		frameForResult.setDefaultCloseOperation(FrameForResult.DISPOSE_ON_CLOSE);
		frameForResult.setTitle(fileName+ " Total Tracetime:"+p.getTraceTime());
		graphViewer.setTitle("Graph For "+fileName);
		frameForResult.setVisible(true);
		graphViewer.setVisible(true);
	}
	private void showComparisionTable(LogComparator lc){
		List<CommonMethods>[] cm = lc.getCommonCriticalMethods();
		JTable[] tables = new TableForComparision(cm).createTablesForComparision();
		JTable table1 = tables[0];
		JTable table2 = tables[1];
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container c = frame.getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));;
		c.add(table1.getTableHeader());
		c.add(new JScrollPane(table1));
		c.add(table2.getTableHeader());
		c.add(new JScrollPane(table2));
		frame.pack();
		frame.setTitle("Comparision");
		frame.setVisible(true);
		
	}
	private void parseWithProgressBar(final File f, final int parserNo) throws InvalidLogFileException{
		final JDialog dialog = new JDialog(frame, true);
		dialog.setUndecorated(true);
		dialog.setLocationRelativeTo(null);
		JProgressBar bar = new JProgressBar();
		bar.setIndeterminate(true);
		bar.setStringPainted(true);
		bar.setString("Parsing "+f.getName());
		dialog.add(bar);
		dialog.pack();
		SwingWorker<Parser, Void> worker = new SwingWorker<Parser, Void>(){

			@Override
			protected Parser doInBackground() throws Exception {
				Parser p = new Parser(f);
				return p;
			}
			@Override
			protected void done(){
				try {
					if(parserNo == 1){
						p1 = get();
					}
					if(parserNo == 2){
						p2 = get();
					}					
				} catch (InterruptedException
						| ExecutionException e) {
					e.printStackTrace();
				}
			    dialog.dispose();
			}
			};
		worker.execute();
		dialog.setVisible(true);		
	}
	
	private void compareWithProgressBar(final Parser p1, final Parser p2){
		final JDialog dialog = new JDialog(frame, true);
		dialog.setLocationRelativeTo(null);
		dialog.setUndecorated(true);
		JProgressBar bar = new JProgressBar();
		bar.setIndeterminate(true);
		bar.setStringPainted(true);
		bar.setString("Now Comparing");
		dialog.add(bar);
		dialog.pack();
		SwingWorker<LogComparator, Void> worker = new SwingWorker<LogComparator, Void>(){

			@Override
			protected LogComparator doInBackground() throws Exception {
				LogComparator lc = new LogComparator(p1, p2);
				return lc;
			}
			@Override
			protected void done(){
				try {
					logComparator = get();				
				} catch (InterruptedException
						| ExecutionException e) {
					e.printStackTrace();
				}
			    dialog.dispose();
			}
			};
		worker.execute();
		dialog.setVisible(true);	
	}

}
