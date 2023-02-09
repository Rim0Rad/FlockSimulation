package main;

import java.awt.Graphics;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Window(int windowSizeX, int windowSizeY, String title) {
		//Graphics window initialisation	
		setTitle(title);
		setSize(windowSizeX, windowSizeY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
        setLocationRelativeTo(null);
		setResizable(false);
		
	}
	
}
