package gui;

import javax.swing.JComboBox;

//TODO: Initial flock number is 0 -> set it to 1

public class CustomComboBox<T> extends JComboBox<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9002781440555609635L;

	CustomComboBox(int FlockNumb){

		
		addItem( ("Flock " + FlockNumb));
        setEditable(false);
		
	}
}
