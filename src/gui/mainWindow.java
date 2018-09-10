package gui;

import java.awt.EventQueue;
import java.io.File;

import parser.*;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JDialog;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;

public class mainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		File fileName = new File("demoLogs/demoMultiThread.log");
		Parser p = new Parser(fileName);
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
				JFileChooser jfc1 = new JFileChooser();
				int returnVal1 = jfc1.showOpenDialog(frame);
			    if(returnVal1 == JFileChooser.APPROVE_OPTION) {
			        Parser p1 = new Parser(jfc1.getSelectedFile());
			        int result = JOptionPane.showConfirmDialog((Component) null, "Wanna Compare with other log?","alert", JOptionPane.YES_NO_OPTION);
			        if( result == JOptionPane.YES_OPTION){
						JFileChooser jfc2 = new JFileChooser();
						int returnVal2 = jfc2.showOpenDialog(frame);
						if(returnVal2 == JFileChooser.APPROVE_OPTION){
					        Parser p2 = new Parser(jfc2.getSelectedFile());
						}
			        }
			        else{
			        	System.out.print("feke");
			        }
			    }
			}
		});
		frame.getContentPane().add(btnFileselect, BorderLayout.NORTH);
	}

}
