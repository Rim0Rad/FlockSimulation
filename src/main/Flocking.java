package main;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import gui.GUI;

public class Flocking {
	
	public static boolean test = false;
	
	//Window settings
	private static int WINDOW_WIDTH = 1500;
	private static int WINDOW_HEIGTH = 500;
	private final String title = "Flocking Simulator";
	
	
	Window window;
	GUI gui;
	Canvas canvas;
	Handler handler;
	
	//Simulation
	private boolean running = true;
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
		Graphics g = bs.getDrawGraphics();
		if(g == null) {
			return;
		}
		
		if(!test) {
			g.setColor(Color.white);
			g.fillRect(0, 0, 1920, 1020);
		}
		
		
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
	
	//Getters and Setters
	public static int getWindowWidth() {
		return WINDOW_WIDTH;
	}
	
	public static int getWindowHeigth() {
		return WINDOW_HEIGTH;
	}
}
