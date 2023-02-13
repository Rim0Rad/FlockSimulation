package boid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.Flocking;
import tools.Coordinates2D;
import tools.Tools;

public class BoidB extends Boid{

	
	private double alignmentStr = 0.0;
	private double separationStr = 0.0;
	private double cohesionStr = 0.0;
	
	private int separationRadius = 50;
	

	public BoidB(int x, int y, double direction) {
		super(x, y, direction);
	}

	public BoidB() {
		super();
	}

	public void tick(double flockAngle) {
		
		if(flockAngle == 0.0 ) {
			//rotate(wonder());
		}else {
			//double tempAngle = flockAngle + wonder() * 0.5;
			//rotate(tempAngle);
		}
		//move();
		
	}
	
	//Flocking algorithm
	public double flock(List<BoidB> boids) {
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
			
			System.out.println("DIRECTION:: " + direction + "  ali: " + alignAngle + " sep: " + sepAngle + " coh: " + cohesAngle);
			return (alignAngle + sepAngle + cohesAngle)/ count;
			
		}else{
			return 0;
		}
		
	}
	
	
	/* Find all of the boids within detection radius */
	private ArrayList<Boid> detect(List<BoidB> boids) {
		
		ArrayList<Boid> boidArr = new ArrayList<Boid>();
		for(Boid boid: boids) {
			if(boid != this && Tools.withinRange(pos, boid.getPos(), detectRadius)) {
				boidArr.add(boid);
			}
		}
		return boidArr;
	}
	
	
	/* Calculates the alignment angle (diff between local flock average direction and current direction*/
	private double calcualteAlignmentAngle(ArrayList<Boid> inRangeBoids) {
		double alignmentAngle = 0.0;
		
		for(Boid boid: inRangeBoids) {
			alignmentAngle += boid.getDirection();	
		}
		
		if(!inRangeBoids.isEmpty()) {
			alignmentAngle /= inRangeBoids.size();
			Tools.angleBetweenDirections(direction, alignmentAngle);
		}
		
		
		return  Tools.wrapAngle(alignmentAngle - this.direction);
	}
	
	
	/* Return the angle between boids direction and the opposite of the center of mass of local flock*/
	private double calculateSeparationAngle(ArrayList<Boid> boids) {
		
		Coordinates2D avgPos = new Coordinates2D(0, 0); 
		double sepAngle = 0.0;
		int detectedBoidCount = 0;
		
		//Detect boids	
		for(Boid boid: boids) {
			if(Tools.withinRange(pos, boid.getPos(), separationRadius)) {
				
				detectedBoidCount++;
				avgPos.add(boid.getPos());
			}
		}
		
		//Calculate the angle
		if(detectedBoidCount != 0) {
			avgPos.divide(detectedBoidCount);
			
			//Calculate separation angle
			// 1. get diff between x and y of center of mass and this boid
			avgPos.subtract(this.pos);
			// 2. atan  y/x to get the angle
			//double angle = 0;
			
			sepAngle = Math.toDegrees(Math.atan2(avgPos.getY(), avgPos.getX()));
			// 3. get correct direction
			//if(avgPos.getY() < 0 && sepAngle > 0) {
			//	sepAngle = -180 - sepAngle;
			//}else if(avgPos.getY() > 0 && sepAngle < 0){
			//	sepAngle = 180 + sepAngle;
			//}
			//4. invert to opposite angle
			//sepAngle = Tools.wrapAngle(sepAngle + 180);
		}
		System.out.println("SepDirection 	" + sepAngle + " dir:	 " + direction + " diferecnce: " + Tools.angleBetweenDirections(direction, sepAngle));
		return Tools.angleBetweenDirections(direction, sepAngle);
	}

	
	private double calculateCohesionAngle(ArrayList<Boid> boids) {
		double cohesionAngle = 0.0;
		Coordinates2D avgPos = new Coordinates2D(0, 0); 
		int count = 0;
		
		for(Boid boid: boids) {
			if(!Tools.withinRange(pos, boid.getPos(), separationRadius)) {
				avgPos.add(boid.getPos());
				count++;
			}
		}
		avgPos.divide(count);
		cohesionAngle = Tools.angle(this.getPos(), avgPos);
		
		return Tools.angleBetweenDirections(direction, cohesionAngle);
	}
	
	public void setAlignmentStr(double value) {this.alignmentStr = value;}
	public void setCohesionStr(double value) {this.cohesionStr = value;	}
	public void setSeparationStr(double value) {this.separationStr = value;}
}
