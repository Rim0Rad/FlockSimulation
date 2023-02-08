package main;

import java.awt.Canvas;
import java.awt.Graphics;

import gui.GUI;

public class Flocking extends Canvas{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8068276333076707032L;
	GUI gui;
	Handler handler;

	private boolean running = true;

	private int timeDelay = 20;
	
	public Flocking(){
		
		handler = new Handler();
		gui = new GUI(handler);
		
		simulation();
	}
	
	public void simulation() {
		while(running) {
			tick();
			render();
			try {
				Thread.sleep(timeDelay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void render() {
		Graphics g = gui.getGraphics();
		if(g == null) {
			return;
		}
		
		
		handler.render(g);
		
		g.dispose();
	}

	private void tick() {
		handler.tick();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Flocking();
	}
}
