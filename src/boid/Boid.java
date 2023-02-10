package boid;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

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
	private double wonderAngVel;
	private double ANGULAR_VEL_MAX = 3;
	private	double ANGULAR_VEL_MIN = -3;
	
	//Boid AI
	private int wonderTimer = 0;
	private int WONDER_TIMER_MAX = 40;
	private int WONDER_TIMER_MIN = 15;
	
	
	//Flocking
	private int detectRadius = 50;
	
	//The Flocking behaviour strength variables:
	private double alignmentStrength = 0.1;
	//private double cohesion = 0.0;
	private double separationStrength = 0.1;
	
	//Test variables
	private int testCount = 0;
	
	
	public Boid(){
		int x = (int) (Math.random() * (Flocking.getWindowWidth() - 200) + 100);
		int y = (int) (Math.random() * (Flocking.getCanvasHeight() - 200) + 100);
		
		this.pos = new Coordinates2D(x, y);
		
		
		this.direction =  Math.random() * (180 + 180) - 180;
		this.color = Color.black;
		
		//Boid boddy
		this.x1 = 5;
		this.x2 = -boidLenght;
		this.x3 = -boidLenght;
		this.y1 = 0;
		this.y2 = - boidWidth;
		this.y3 = boidWidth;
		
		this.wonderAngVel = 0;
		this.vel = 1;
	}
	
	
	public void tick(double flockAngle) {
		//Bits for testing 
		if(Flocking.test) {
			if(testCount == 1) {	
				rotate(90);
			}else if(testCount == 2) {
				this.vel = 50;
				move();
			}
			testCount++;
				
		}else {
			move();
			double avoidWalsAngle = avoidWalls();
			if(avoidWalsAngle != 0) {
				rotate(avoidWalsAngle);
				
			}else if(flockAngle == 0.0) {
				 
				rotate(wonder());
			}else {
				double wonderAng = wonder();
				double tempAngle = flockAngle + wonderAng;
				
				//System.out.println("Flocking turn angle:  " + tempAngle + " flockAngle " + flockAngle + " wonderAng" + wonderAng);
				rotate(tempAngle);
			}
			
			
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
	
	private void rotate(double angVel) {
			direction = Tools.wrapAngle(direction += angVel);
			//System.out.println("Direction: " + direction);
			
			double radiants = Math.toRadians(-direction);
			x1 =  (boidLenght * Math.cos(radiants) + (-0) * Math.sin(radiants));
			y1 =  (boidLenght * -Math.sin(radiants) + (-0) * Math.cos(radiants));
			
			x2 =  (-boidLenght * Math.cos(radiants) + (-boidWidth) * Math.sin(radiants));
			y2 =  (-boidLenght * -Math.sin(radiants) + (-boidWidth) * Math.cos(radiants));
			
			x3 =  (-boidLenght * Math.cos(radiants) + boidWidth * Math.sin(radiants));
			y3 =  (-boidLenght * -Math.sin(radiants) + boidWidth * Math.cos(radiants));
			//System.out.println("Rotation complete");
		//}
	}
	
	private double wonder(){
		// at random point change the angular velocity of the boid within a range
		if(wonderTimer-- <=  0) {
			wonderTimer = (int) ((Math.random() * (WONDER_TIMER_MAX - WONDER_TIMER_MIN)) + WONDER_TIMER_MIN);
			wonderAngVel = ((Math.random() * (ANGULAR_VEL_MAX - ANGULAR_VEL_MIN)) + ANGULAR_VEL_MIN);
			return  wonderAngVel;
		} else {
			return wonderAngVel;
		}
	}
	
	
	//Flocking algorithm
	// 1. Start with alignment
	public double flock(List<Boid> boids) {
		double alignAngle = 0.0;
		double seperateAngle = 0.0;
		int count = 0;
		
		double alignmentAngle = calcualteAlignmentAngle(boids);
		double separationAngle = calculateSeparationAngle(boids);
		
		if(alignmentAngle != 0) {
			double alignmentDifference = -Tools.wrapAngle(direction - alignmentAngle);
			alignAngle = alignmentDifference * alignmentStrength;
			count++;
		}
		if(separationAngle != 0) {
			seperateAngle = separationAngle * separationStrength;
			count++;
		}
		
		if(count > 0) {
			return (alignAngle + seperateAngle)/ count;
		}else {
			return 0;
		}
	}
	
	
	//TODO: refactor by making a detection method that returns an array of detected boids
	private double calcualteAlignmentAngle(List<Boid> boids) {
		double alignmentAngle = 0.0;
		int detectedBoidCount = 0;
		
		for(Boid boid: boids) {
			
			if(this!= boid) {
				if(Tools.withinRange(pos, boid.getPos(), detectRadius)) {
					detectedBoidCount++;
					alignmentAngle += boid.getDirection();
				}
			}
		}
		if(detectedBoidCount != 0) {
			alignmentAngle /= detectedBoidCount;
		}
		return alignmentAngle;
	}
	
	/* Return the angle between boids direction and the oposite of the center of mass of local flock*/
	private double calculateSeparationAngle(List<Boid> boids) {
		Coordinates2D avgPos = new Coordinates2D(0, 0); 
		double separationAngle = 0.0;
		int detectedBoidCount = 0;
	//Detect boids	
		for(Boid boid: boids) {
			if(this!= boid) {
				if(Tools.withinRange(pos, boid.getPos(), detectRadius)) {
					detectedBoidCount++;
					avgPos.add(boid.getPos());
				}
			}
		}
		
		if(detectedBoidCount != 0) {
			avgPos.divide(detectedBoidCount);
		//Calculate separation angle
		// 1. get diff between x and y of center of mass and this boid
			avgPos.subtract(this.pos);
		// 2. atan  y/x to get the angle
			double angle = 0;
			if(avgPos.getX() == 0) {
				if(avgPos.getY() < 0) {
					angle = -90;
				}else if(avgPos.getY() > 0){
					angle = 90;
				}
			}else {
				angle = Math.atan(avgPos.getY()/avgPos.getX());
			}
			
		// 3. get corret direction
			if(avgPos.getY() < 0 && angle < 0) {
				angle = angle;
			}else if(avgPos.getY() < 0 && angle > 0) {
				angle = -180 - angle;
			}else if(avgPos.getY() > 0 && angle < 0){
				angle = 180 + angle;
			}else if(avgPos.getY() > 0 && angle > 0) {
				angle = angle;
			}
		//4. invert to opposite angle
			if(angle < 0) {
				angle += 180;
			}else if(angle >= 0) {
				angle -= 180;
			}
		//5 desired angle - current angle = separation angle
			separationAngle = angle - direction;
		}
		
		return separationAngle;
	}
	/*TODO: Fixed the corner problem, finds ways to refactor
	*/		
	private double avoidWalls(){
		double angVelX = 0.0;
		double angVelY = 0.0;
		
	/* Top side of the screen */
		if(pos.getY() - detectRadius <= 0) {
			angVelY = 4;
			if(direction > -90 && direction <= 0) {
				//angVelY = Math.abs(angVelY);
			}else if(direction < -90 && direction >= -180 || direction <= 180 && direction > 90 ) {
				angVelY = -angVelY;
			}else if(direction == -90) {
				angVelY = -angVelY;
			}else {}
			if(pos.getY() <= 0) {
				System.out.println("======BOID LEFT THE SCREEN!: TOP");
				this.color = Color.red;
				Flocking.stop();
			} 
	/* Bottom side of the screen */
		}else if((pos.getY() + detectRadius) >= Flocking.getCanvasHeight()) {
			angVelY = 4;
			if(direction < 90 && direction >= -90) {
				angVelY = -angVelY;
			}else if(direction > 90 && direction <= 180) {
				//angVelY = angVelY;
			}else if(direction == 90) {
				angVelY = -angVelY;
			}else {}
			if(pos.getY() >= Flocking.getCanvasHeight()) {
				System.out.println("=======BOID LEFT THE SCREEN!: BOTTOM");
				this.color = Color.red;
				Flocking.stop();
			}
	/* Right side of the screen */	
		}else if((pos.getX() + detectRadius) >= Flocking.getCanvasWidth()) {
			angVelX = 4;
			if(direction > 0 && direction <= 90) {
				//angVelX = angVelX;
			}else if(direction < 0 && direction > -180) {
				angVelX = -angVelX;
			}else if(direction == 0) {
				angVelX = -angVelX;
			}else {
				
			}
			if(pos.getX() >= Flocking.getWindowWidth()) {
				System.out.println("==========BOID LEFT THE SCREEN!: RIGHT");
				this.color = Color.red;
				Flocking.stop();
			}
	/* Left side of the screen */
		}else if((pos.getX() - detectRadius) <= 0) {
			angVelX = 4;
			if(direction > -180 && direction <= -90) {
				//angVelX = Math.abs(angVelX);
			}else if(direction < 180 && direction >= 0) {
				angVelX = -angVelX;
			}else if(direction == 180 || direction == -180) {
				angVelX = -angVelX;
			}else {
				
			}
			if(pos.getX() >= Flocking.getWindowWidth()) {
				System.out.println("=========BOID LEFT THE SCREEN!: LEFT");
				this.color = Color.red;
				Flocking.stop();
			}
		}
		return angVelX + angVelY;
	}
	//Getters and Setters
	private Coordinates2D getPos() {
		return pos;
	}

	private double getDirection() {
		return direction;
	}
}
