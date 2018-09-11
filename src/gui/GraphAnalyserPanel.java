package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.jfree.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import gui.ChartBuilder;
public class GraphAnalyserPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public GraphAnalyserPanel() {
       JFreeChart chart = new ChartBuilder().getChart();
       ChartPanel cp = new ChartPanel(chart);
       this.setLayout(new java.awt.BorderLayout());
       this.add(cp,BorderLayout.CENTER);
	}

}
