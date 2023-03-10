package main;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import gui.CustomCanvas;
import gui.GUI;

public class Flocking {
	
	

	//TODO: compatibility - make the initial screen a percentage of the screen size
	private static int WINDOW_WIDTH = 1500;
	private static int WINDOW_HEIGTH = 830;
	
	/*Graphical elements */
	private static Window window;
	private final String TITLE = "Flocking Simulator";
	
	private static GUI GUI;
	private static CustomCanvas canvas;
	
	private Handler handler;
	
	/* Simulation parameters*/
	private static boolean running = true;
	private int timeDelay = 20;  //TODO: will be looking into a better simulation loop as this static delay doesn't account for increasing number of boids. Starts to lag pretty soon.
	
	
	public Flocking(){
		
		window = new Window(WINDOW_WIDTH, WINDOW_HEIGTH, TITLE);
		handler = new Handler();
		GUI = new GUI(handler, window);
		simulation();
	}
	
	/* Runs the simulation loop*/
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
	
	/* Draw objects onto canvas */
	private void render() {
		
		this.canvas = GUI.getCanvas();
		
		BufferStrategy bs = canvas.getBufferStrategy();
		
		if(bs == null) {
			canvas.createBufferStrategy(2);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		if(g == null) {
			return;
		}
		
		//Draw transparent background for trail effect TODO: slider for adjustable background transparency to control the trailing of the boid
		g.setColor(new Color(0, 0, 0, .1f));
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		//Draw boids
		handler.render(g);
		
		//Draw wall detection zone lines
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.yellow);
		g.drawLine(0, 50, canvas.getWidth(), 50);
		g.drawLine(50, 0, 50, canvas.getHeight());
		g.drawLine(0,canvas.getHeight()-50, 2000, canvas.getHeight() - 50);
		g.drawLine(canvas.getWidth() - 50, 0, canvas.getWidth() - 50, canvas.getHeight());
		
		
		g.dispose();
		
		//NOTE: creating new window when entering full screen mode sometimes happen in between the rendering which causes bs state change which throws an error
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
	
	/*Stop the simulation from running */
	public static void stop() {
		running = false;
		
	}
	
	
	/************************
	 * 
	 * Getters and Setters
	 * 
	 ************************/
	
		public static int getWindowWidth() {return WINDOW_WIDTH;}
		public static int getWindowHeigth() {return WINDOW_HEIGTH;}
		public static int getCanvasHeight() {return canvas.getHeight();}
		public static int getCanvasWidth() {return canvas.getWidth();}
}
