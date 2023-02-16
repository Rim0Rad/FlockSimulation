package main;


import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.File;

import gui.GUI;

public class Flocking {
	
	public static boolean test = false;
	
	//Window settings
	private final String title = "Flocking Simulator";
	private static int WINDOW_WIDTH = 1500;
	private static int WINDOW_HEIGTH = 830;
	
	//private static int CANVAS_HEIGTH;
	//private static int CANVAS_WIDTH;
	
	
	public static Window window;
	private static GUI gui;
	private Canvas canvas;
	private Handler handler;
	
	//Simulation
	private static boolean running = true;
	private int timeDelay = 20;
	
	public Flocking(){
		
		window = new Window(WINDOW_WIDTH, WINDOW_HEIGTH, title);
		canvas = new Canvas();
		handler = new Handler();
		gui = new GUI(canvas, handler, window);
		simulation();
	}
	
	public void simulation() {
		while(running) {
			
			tick();
			render();
			
			try {
				Thread.sleep(timeDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void render() {
		BufferStrategy bs = canvas.getBufferStrategy();
		if(bs == null) {
			canvas.createBufferStrategy(2);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		if(g == null) {
			return;
		}
		
		
		//Background color
		g.setColor(new Color(0, 0, 0, .1f));
		g.fillRect(0, 0, getCanvasWidth(), getCanvasHeight());
		
		
		//Draw wall detection zone lines
	/*	int offest = 150;
		g.setStroke(new BasicStroke());
		g.setColor(Color.yellow);
		g.drawLine(0, offest, getCanvasWidth(), offest);
		g.drawLine(offest, 0, offest, getCanvasHeight());
		g.drawLine(0, getCanvasHeight()-offest, getCanvasWidth(), getCanvasHeight() - offest);
		g.drawLine(getCanvasWidth() - offest, 0, getCanvasWidth() - offest, getCanvasHeight());
*/
		g.setStroke(new BasicStroke());
		handler.render(g);
		
		//Draw wall detection zone lines
/*		g.setStroke(new BasicStroke(3));
		g.setColor(Color.yellow);
		g.drawLine(0, 50, getCanvasWidth(), 50);
		g.drawLine(50, 0, 50, getCanvasHeight());
		g.drawLine(0, getCanvasHeight()-50, 2000, getCanvasHeight() - 50);
		g.drawLine(getCanvasWidth() - 50, 0, getCanvasWidth() - 50, getCanvasHeight());
*/		
		
		g.dispose();
		
		//NOTE: creating new window when entering fullscreen mode sometimes happen in between the rendering which causes bs state change which throws an error
		
		try {
			bs.show();
		} catch(Exception e) {
			System.out.println(e);
		}
		
		
		
	}

	private void tick() {
		handler.tick();
	}

	public static void main(String[] args) {
		new Flocking();
	}
	
	public static void stop() {running = false;}
	
	
	//Getters and Setters
		public static int getWindowWidth() {return WINDOW_WIDTH;}
		public static int getWindowHeigth() {return WINDOW_HEIGTH;}
		public static int getCanvasHeight() {
			return gui.getCanvasHeigth();
		}
		public static int getCanvasWidth() {
			return gui.getCanvasWidth();
		}
}
