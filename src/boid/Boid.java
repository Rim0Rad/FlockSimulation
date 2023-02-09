package boid;

import java.awt.Color;
import java.awt.Graphics;

import main.Flocking;
import tools.Coordinates2D;
import tools.Tools;



public class Boid {
	
	//Visual
	private Color color;
	private int boidLenght = 8;
	private int boidWidth = 5;
	
	//Position
	private Coordinates2D pos;
	private double x1, x2, x3, y1, y2, y3;
	
	//Movement
	private int vel;
	private double direction;
	private double angularVel;
	private double ANGULAR_VEL_MAX = 5;
	private	double ANGULAR_VEL_MIN = -5;
	
	//Boid AI
	private int wonderTimer;
	private int WONDER_TIMER_MAX = 40;
	private int WONDER_TIMER_MIN = 10;
	
	private int avoidRadius = 50;
	
	
	//Flocking
	private int detectRadius = 100;
	private int separationRadius = 50;
	
	//The Flocking behaviour strength variables:
	private double alignment = 0.0;
	private double cohesion = 0.0;
	protected double separation = 0.0;
	
	//Test variables
	private int testCount = 0;
	
	
	public Boid(){
		this.pos = new Coordinates2D(500, 200);
		
		this.color = Color.black;
		this.direction = 0;
		this.x1 = 5;
		this.x2 = -boidLenght;
		this.x3 = -boidLenght;
		this.y1 = 0;
		this.y2 = - boidWidth;
		this.y3 = boidWidth;
		
		this.angularVel = 1;
		this.vel = 1;
	}
	
	
	public void tick() {
		
		if(Flocking.test) {
			if(testCount == 1) {
				this.angularVel = -90;
				rotate();
			}else if(testCount == 2) {
				this.vel = 50;
				move();
			}
			testCount++;
				
		}else {
			wonder();
			rotate();
			move();
		}
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.drawPolygon(new int[] {(int) (pos.getX()+x1), (int) (pos.getX() + x2), (int) (pos.getX() + x3)}, new int[] {(int) (pos.getY()+y1), (int) (pos.getY() + y2), (int) (pos.getY() + y3)}, 3);
		g.fillOval((int)pos.getX(), (int) pos.getY(), 2, 2);
	
	}
	
	private void move() {
		// update x and y positions based on the angle the boid is currently facing
		double rad = Math.toRadians(direction);
		pos.add(vel * Math.cos(rad), vel * Math.sin(rad));
	}
	
	
	private void rotate() {
		if(angularVel != 0) {
			direction = Tools.wrapAngle(direction += angularVel);
			
			
			double radiants = Math.toRadians(-direction);
			x1 =  (boidLenght * Math.cos(radiants) + (-0) * Math.sin(radiants));
			y1 =  (boidLenght * -Math.sin(radiants) + (-0) * Math.cos(radiants));
			
			x2 =  (-boidLenght * Math.cos(radiants) + (-boidWidth) * Math.sin(radiants));
			y2 =  (-boidLenght * -Math.sin(radiants) + (-boidWidth) * Math.cos(radiants));
			
			x3 =  (-boidLenght * Math.cos(radiants) + boidWidth * Math.sin(radiants));
			y3 =  (-boidLenght * -Math.sin(radiants) + boidWidth * Math.cos(radiants));
		}
	}
	
	private void wonder(){
		// at random point change the angular velocity of the boid within a range
		if(wonderTimer-- <=  0) {
			wonderTimer = (int) ((Math.random() * (WONDER_TIMER_MAX - WONDER_TIMER_MIN)) + WONDER_TIMER_MIN);
			angularVel = (int) ((Math.random() * (ANGULAR_VEL_MAX - ANGULAR_VEL_MIN)) + ANGULAR_VEL_MIN);
		}
		//TODO: wonder away from edges
	}
	
	
	//Getters and Setters
	private Coordinates2D getPos() {
		return pos;
	}

	private double getDirection() {
		return direction;
	}
}
