package main;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
}
