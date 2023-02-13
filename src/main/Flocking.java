package main;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;

import gui.GUI;

public class Flocking {
	
	public static boolean test = false;
	
	//Window settings
	private static int WINDOW_WIDTH = 1500;
	private static int WINDOW_HEIGTH = 830;
	
	private static int CONTENT_PANE_WIDTH;
	private static int CONTENT_PANE_HEIGHT;
	
	private static int CANVAS_HEIGTH;
	private static int CANVAS_WIDTH;
	
	private final String title = "Flocking Simulator";
	
	Window window;
	GUI gui;
	Canvas canvas;
	Handler handler;
	
	//Simulation
	private static boolean running = true;
	private int timeDelay = 20;
	
	public Flocking(){
		
		window = new Window(WINDOW_WIDTH, WINDOW_HEIGTH, title);
		
		CONTENT_PANE_WIDTH = window.getContentPane().getWidth();
		CONTENT_PANE_HEIGHT = window.getContentPane().getHeight();
		CANVAS_HEIGTH = CONTENT_PANE_HEIGHT-35;
		CANVAS_WIDTH = CONTENT_PANE_WIDTH;
		
		canvas = new Canvas();
		canvas.setSize(WINDOW_WIDTH, CANVAS_HEIGTH);
		
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
		
		if(!test) {
			g.setColor(Color.white);
			g.fillRect(0, 0, 1920, 1020);
		}
		
		
		//Draw wall detection zone lines
		g.setColor(Color.yellow);
		g.drawLine(0, 50, 2000, 50);
		g.drawLine(50, 0, 50, CANVAS_HEIGTH);
		g.drawLine(0, CANVAS_HEIGTH-50, 2000, CANVAS_HEIGTH - 50);
		g.drawLine(CONTENT_PANE_WIDTH - 50, 0, CONTENT_PANE_WIDTH - 50, CANVAS_HEIGTH);
		
		
		g.setColor(Color.black);
		handler.render(g);
		g.drawLine(0, 200, 1500, 200);
		
		
		
		g.dispose();
		bs.show();
		
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
	public static int getCanvasHeight() {return CANVAS_HEIGTH;}
	public static int getCanvasWidth() {return CANVAS_WIDTH;}
}
