package gui;

import java.awt.Color;

import javax.swing.JCheckBox;

//TODO: Customise the check box


public class CustomCheckBox extends JCheckBox{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6126028489343436388L;

	Color CHECK_BOX_BG_COLOR = new Color(100, 120, 250);
	Color CHECK_BOX_FG_COLOR = Color.white;
	
	//TODO: Customise the check box
	CustomCheckBox(String title){
		super(title);
		
		setBackground(CHECK_BOX_BG_COLOR);
        setForeground(CHECK_BOX_FG_COLOR);
	}
	
	
}
