package main;

import javax.swing.JFrame;

public class Window extends JFrame {
	
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
