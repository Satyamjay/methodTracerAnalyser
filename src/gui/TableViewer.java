package gui;

import java.awt.BorderLayout;

import parser.Parser;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartPanel;

public class TableViewer extends JFrame {

	private JPanel contentPane;
	/**
	 * Create the frame.
	 */
	public TableViewer(Parser p) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		// Building table of analyzed log file
		TableBuilder table = new TableBuilder(p);
	    this.setSize(300, 300);
	    this.add(new JScrollPane(table.getTable()));
	    this.setVisible(true);
		
	}
	

}
