package gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//TODO: fix full screen - (hierarchical changes broke it) 

public class CustomCanvas extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5096494187237692302L;

	private boolean fullscreen = false;
	
	
	public CustomCanvas() {
		super();
		
		// ESC - to exit full screen mode
		addKeyListener(CANVAS_KEY_LISTENER);
	}
	
	
	/* Recalculate canvas size for a new window */
	
	public void updateSize(Dimension contentSize, Dimension botPanelSize) {
		if(fullscreen) {
			this.setSize(contentSize);
		}else{
			this.setSize((int)contentSize.getWidth() , (int)(contentSize.getHeight() - botPanelSize.getHeight()));
		}
	}
	
	public void togleFullScreen(){
		fullscreen = (fullscreen) ? false: true;
		GUI.togleFullScreen();
	}
	
	
	/*******************
	 * Canvas Listeners *
	 *******************/
	
	
	/* TODO: KEYLISTENER - listens  for ESC key to exit full screen mode 
	 * probably best to move the listener declaration out to GUI */
	
	KeyListener CANVAS_KEY_LISTENER = new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE && fullscreen == true) {
				//fullscreen = GUI.togleFulscreen(fullscreen);
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {}
		
	};
	
}
