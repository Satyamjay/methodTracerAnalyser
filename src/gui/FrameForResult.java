package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import org.jfree.chart.*;

public class FrameForResult extends JFrame {

	private JPanel contentPane;
	/**
	 * Create the frame.
	 */
	public FrameForResult() {
		this.setBounds(100, 100, 800, 600);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		setContentPane(contentPane);
		
	}

}
