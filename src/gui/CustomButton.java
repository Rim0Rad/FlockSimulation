package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class CustomButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5462747865784237705L;
	
	private Font font = new Font(null, 0, 10);
	
	/*Button setup */
	 CustomButton(String title, Color background, Color foreground) {
		super(title);
		setFont(font);
		setBackground(background);
		setForeground(foreground);
		setFocusPainted(false);
		
		

		setMaximumSize(new Dimension(100 , 15));
		
		//setAlignmentX(0.5f);
		//setAlignmentY(0.5f);
		
	}
	
}
