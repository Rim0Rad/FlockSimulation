package main;


import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;

import gui.GUI;

public class Flocking {
	
	//Window settings
	private final String title = "Flocking Simulator";
	private static int WINDOW_WIDTH = 1500;
	private static int WINDOW_HEIGTH = 830;
	
	private static Window window;
	private static GUI gui;
	//private Canvas canvas;
	private Handler handler;
	
	//Simulation
	private static boolean running = true;
	private int timeDelay = 20;
	
	public Flocking(){
		
		window = new Window(WINDOW_WIDTH, WINDOW_HEIGTH, title);
		//canvas = new Canvas();
		handler = new Handler();
		gui = new GUI(handler, window);
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
		Canvas canvas = gui.getCanvas();
		BufferStrategy bs = canvas.getBufferStrategy();
		if(bs == null) {
			canvas.createBufferStrategy(2);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		if(g == null) {
			return;
		}
			
		//Set background color
		g.setColor(new Color(0, 0, 0, .1f));
		g.fillRect(0, 0, getCanvasWidth(), getCanvasHeight());
		
		//Draw boids
		handler.render(g);
		
		//Draw wall detection zone lines
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.yellow);
		g.drawLine(0, 50, getCanvasWidth(), 50);
		g.drawLine(50, 0, 50, getCanvasHeight());
		g.drawLine(0, getCanvasHeight()-50, 2000, getCanvasHeight() - 50);
		g.drawLine(getCanvasWidth() - 50, 0, getCanvasWidth() - 50, getCanvasHeight());
		
		
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
	
	public static void stop() {running = false;}
	
	
	//Getters and Setters
		public static int getWindowWidth() {return WINDOW_WIDTH;}
		public static int getWindowHeigth() {return WINDOW_HEIGTH;}
		public static int getCanvasHeight() {return gui.getCanvasHeigth();}
		public static int getCanvasWidth() {return gui.getCanvasWidth();}
}
