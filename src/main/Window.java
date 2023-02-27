package main;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -75504316500988620L;


	public Window(int windowSizeX, int windowSizeY, String title) {	
		setTitle(title);
		setSize(windowSizeX, windowSizeY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
        setLocationRelativeTo(null);
		setResizable(true);
	}

	public Window(int windowSizeX, int windowSizeY, String title, boolean decoration) {
		setUndecorated(decoration);
		setVisible(false);
        setLocationRelativeTo(null);
		setResizable(false);
	}
	

	/* Return the size of the content pane */
	public Dimension getContentSize() {
		System.out.println(this.getContentPane().getSize());
		return this.getContentPane().getSize();
	}
}
