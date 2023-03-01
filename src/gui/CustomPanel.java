package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


//TODO: constructor with size and colour input
public class CustomPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1590939027059049080L;

	/* Set up a JPanel*/
	CustomPanel() {
		
		setLayout(new BoxLayout(this, 1));
		setBorder(new LineBorder(Color.white));
		setBackground(Color.blue);
		setPreferredSize(new Dimension(200, 90));
	
	}
}
