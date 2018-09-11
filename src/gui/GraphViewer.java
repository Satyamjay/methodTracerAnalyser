package gui;

import java.awt.BorderLayout;
import parser.Parser;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartPanel;

public class GraphViewer extends JFrame {

	private JPanel contentPane;
	/**
	 * Create the frame.
	 */
	public GraphViewer(Parser p) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		ChartBuilder chart = new ChartBuilder(p);
		ChartPanel cp = new ChartPanel(chart.getChart());
		contentPane.setLayout(new java.awt.BorderLayout());
		contentPane.add(cp, BorderLayout.CENTER);
		setContentPane(contentPane);
		
	}

}
