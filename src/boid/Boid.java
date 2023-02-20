package boid;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.Flocking;
import tools.Coordinates2D;
import tools.HSBColor;
import tools.Tools;
import tools.Triangle;

public class Boid {
	
	//Visual
	protected HSBColor color;
	
	private int size;
	private int boidLenght;
	private int boidWidth;
	private Triangle body;
	
	//Position
	protected Coordinates2D pos;
	
	//Movement
	private int speed;
	protected double direction;
	private double wonderAngVel;
	
	//Boid AI
	private int wonderTimer = 0;
	private int WONDER_TIMER_MAX = 50;
	private int WONDER_TIMER_MIN = 20;
	
	//Wall detection radius
	protected int detectRadius = 100;
	BufferedImage buffImg;
	
	public Boid(int x, int y, double direction) {
		this.pos = new Coordinates2D(x, y);
		this.direction = direction;
		
		size = 10;
		boidLenght = size;
		boidWidth = (int) size / 3;
		body = new Triangle(boidLenght, boidWidth, direction);
		color = new HSBColor(0, 0 ,0);
		rotate(0);	
	}
	
	public Boid(){
		int x = (int) (Math.random() * (Flocking.getWindowWidth() - 200) + 100);
		int y = (int) (Math.random() * (Flocking.getCanvasHeight() - 200) + 100);
		this.pos = new Coordinates2D(x, y);
		
		size = 10;
		boidLenght = size;
		boidWidth = (int) size / 3;
		
		this.body = new Triangle(boidLenght, boidWidth, direction);
		
		this.direction =  Math.random() * (180 + 180) - 180;
		this.color =  new HSBColor(0.2f, 1 ,1);
		
		this.wonderAngVel = 0;
		this.speed = 1;
		
	}
	
	/* Models the behaviour of a boid.
	 *  
	 *  Second: random movement of free boid
	 *  
	 *  */
	public void tick() {
		
		double avoidWalsAngle = avoidWalls();
		if(avoidWalsAngle != 0) {
			rotate(avoidWalsAngle);
		}else{
			rotate(wonder());
		}
		
		move();
		
	}
	
	public void render(Graphics g) {
		g.setColor(color.getColor());
		g.fillPolygon(body.relativeToX(pos.getX()), body.relativeToY(pos.getY()), 3);
		//g.fillp
	}
	
	/*Update x and y positions based boids direction */
	protected void move() {
		double rad = Math.toRadians(direction);
		
		
		pos.add(speed * Math.cos(rad), speed * Math.sin(rad));
		pos.wrap(Flocking.getCanvasWidth(), Flocking.getCanvasHeight());
	}
	
	/*Changes the direction of the boid and rotates the body */
	protected void rotate(double angVel) {
		direction = Tools.wrapAngle(direction += angVel);
		body.Rotate(direction);
	}
	
	/*Simulates a random direction change of the boid */ 
	protected double wonder(){
		if(wonderTimer-- <=  0) {
			wonderTimer = (int) ((Math.random() * (WONDER_TIMER_MAX - WONDER_TIMER_MIN)) + WONDER_TIMER_MIN);
			wonderAngVel = ((Math.random() * 5) - 2);
			return  wonderAngVel;
		} else {
			return wonderAngVel;
		}
	}
	
	/*Avoid the edges of the canvas */
	protected double avoidWalls(){
		double angVel = 0.0;
	
		/* Top side of the screen */
		if( pos.getY() - detectRadius <= 0) {
			angVel = 4;
			
			if(direction < -90 && direction >= -180 || direction <= 180 && direction > 90 ) {
				angVel = -angVel;
			}else if( direction == -90 ) {
				angVel *= new Random().nextBoolean() ? -1 : 1;
			}

			/* Bottom side of the screen */
		}else if( pos.getY() + detectRadius >= Flocking.getCanvasHeight()) {
			angVel = 4;
			
			if( direction < 90 && direction >= -90 ) {
				angVel = -angVel;
			} else if( direction == 90 ) {
				angVel *= new Random().nextBoolean() ? -1 : 1;
			}

			/* Right side of the screen */	
		}else if((pos.getX() + detectRadius) >= Flocking.getCanvasWidth()) {
			angVel = 4;
			
			if(direction < 0 && direction > -180) {
				angVel = -angVel;
			}else if( direction == 0 ) {
				angVel *= new Random().nextBoolean() ? -1 : 1;
			}
			/* Left side of the screen */
		}else if((pos.getX() - detectRadius) <= 0) {
			angVel = 4;
			
			if(direction < 180 && direction >= 0) {
				angVel = -angVel;
			}else if(direction == 180 || direction == -180) {
				angVel *= new Random().nextBoolean() ? -1 : 1;
			}
		}
		return angVel;
	}
	
	
/* ==== Getters and Setters ==== */
	protected Coordinates2D getPos() {
		return pos;
	}
	public double getDirection() {
		return direction;
	}

	public void setSize(int size) {
		this.size = size;
		this.body.reSize(size);
	}
}
