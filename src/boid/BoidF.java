package boid;

import java.util.ArrayList;
import java.util.List;

import tools.Coordinates2D;
import tools.HSBColor;
import tools.Tools;

public class BoidF extends Boid{

	
	private double alignmentStr = 0.0;
	private double separationStr = 0.0;
	private double cohesionStr = 0.0;
	
	private int separationRadius = 50;
	

	public BoidF(int x, int y, double direction) {
		super(x, y, direction);
	}
	/* Create a boid at random position and direction */
	public BoidF() {
		super();
	}

	public void tick(double flockAngle) {
		
		if(flockAngle == 0.0 ) {
			rotate(wonder());
		}else {
			double tempAngle = flockAngle + wonder() * 0.5;
			rotate(tempAngle);
		}
		move();
		
	}
	
	//Flocking algorithm
	public double flock(List<BoidF> boids) {
		double alignAngle = 0.0;
		double sepAngle = 0.0;
		double cohesAngle = 0.0;
		
		int count = 0;
		
		ArrayList<Boid> boidsDetected = detect(boids);
		if(!boidsDetected.isEmpty()) {
			
			alignAngle = calcualteAlignmentAngle(boidsDetected);
			if(alignAngle != 0) {
				alignAngle = alignAngle * alignmentStr;
				count++;
			}
		
			sepAngle = calculateSeparationAngle(boidsDetected);
			if(sepAngle != 0) {
				sepAngle = Tools.wrapAngle(sepAngle);
				sepAngle = sepAngle * separationStr;
				count++;
			}
			
			cohesAngle = calculateCohesionAngle(boidsDetected);
			if(cohesAngle != 0) {
				cohesAngle = Tools.wrapAngle(cohesAngle);
				cohesAngle = cohesAngle * cohesionStr;		
				count++;
			}	
			return (alignAngle + sepAngle + cohesAngle)/ count;
			
		}else{
			return 0;
		}
	}
	
	/*Find all of the boids within detection radius*/
	private ArrayList<Boid> detect(List<BoidF> boids) {
		ArrayList<Boid> boidArr = new ArrayList<Boid>();
		for(Boid boid: boids) {
			if(boid != this && Tools.withinRange(pos, boid.getPos(), detectRadius)) {
				boidArr.add(boid);
			}
		}
		return boidArr;
	}

	
	/*Calculates the alignment angle (diff between local flock average direction and current direction*/
	private double calcualteAlignmentAngle(ArrayList<Boid> inRangeBoids){
		double alignmentAngle = 0.0;
		
		for(Boid boid: inRangeBoids) {
			alignmentAngle += boid.getDirection();	
		}
		
		if(!inRangeBoids.isEmpty()) {
			alignmentAngle /= inRangeBoids.size();
		}
		return  Tools.wrapAngle(alignmentAngle - this.direction);
	}

	/* Return the angle between boids direction and the opposite of the center of mass of local flock*/
	private double calculateSeparationAngle(ArrayList<Boid> boids) {
		
		Coordinates2D avgPos = new Coordinates2D(0, 0); 
		double sepAngle = 0.0;
		int count = 0;
		
		//Detect boids	
		for(Boid boid: boids) {
			if(Tools.withinRange(pos, boid.getPos(), separationRadius)) {
				
				count++;
				avgPos.add(boid.getPos());
			}
		}
		
		//Calculate the angle
		if(count != 0) {
			avgPos.divide(count);
			avgPos.subtract(this.pos);
			sepAngle = Math.toDegrees(Math.atan2(avgPos.getY(), avgPos.getX()));
			sepAngle = Tools.wrapAngle(this.direction - sepAngle);
		}
		return sepAngle;
	}

	private double calculateCohesionAngle(ArrayList<Boid> boids) {
		double cohesAngle = 0.0;
		Coordinates2D avgPos = new Coordinates2D(0, 0); 
		int count = 0;
		
		for(Boid boid: boids) {
			if(!Tools.withinRange(pos, boid.getPos(), separationRadius)) {
				avgPos.add(boid.getPos());
				count++;
			}
		}
		if(count!= 0) {
			avgPos.divide(count);
			avgPos.subtract(this.pos);
			cohesAngle = Math.toDegrees(Math.atan2(avgPos.getY(), avgPos.getX()));
			cohesAngle = Tools.wrapAngle(cohesAngle - this.direction);
		}
		return cohesAngle;
	}
	
	
	public void setColor(HSBColor hsbColor) {this.color = hsbColor;}
	public void setColor(int value) {
		this.color.setHue(value);
	}
	public int getColor() {return this.color.getHue();}
	
	public void updateParameters(double value, double value2, double value3) {
		setAlignmentStr(value*0.01);
		setCohesionStr(value2*0.01);
		setSeparationStr(value3*0.01);
	}
	public void setAlignmentStr(double value) {this.alignmentStr = value;}
	public void setCohesionStr(double value) {this.cohesionStr = value;	}
	public void setSeparationStr(double value) {this.separationStr = value;}
	public int getAlignment() {return (int) (this.alignmentStr * 100);}
	public int getCohesion() {return (int) (this.cohesionStr * 100);}
	public int getSeparation() {return (int) (this.separationStr * 100);}
	
	
}
