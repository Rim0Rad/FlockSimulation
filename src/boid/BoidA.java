package boid;

import java.util.ArrayList;
import java.util.List;

import tools.Coordinates2D;
import tools.Tools;


public class BoidA extends Boid{

	
	private int detectRadius = 100;
	
	//The Flocking behaviour strength variables:
	private double aliStr = 0.5;
	private double cohStr = 0.1;
	private double sepStr = 0.1;
	
	public double flock(List<BoidA> boids) {
		
		double avgAli = 0.0;
		int aliCount = 0;
		double aliAngle = 0.0;
		
		Coordinates2D avgCoh = new Coordinates2D(0,0);
		int cohCount = 0;
		double cohAngle = 0.0;
		
		Coordinates2D avgSep = new Coordinates2D(0,0);
		int sepCount = 0;
		double sepAngle = 0.0;
		
		int avgCount = 0; 
		
		double distance = 0.0;
		for(Boid boid: boids) {
			if(boid != this) {
				//Calculate alignment
				distance = Tools.distance(this.pos, boid.getPos());
				if(distance <= detectRadius) {
					avgAli += boid.getDirection();
					aliCount++;
					if(distance >= detectRadius/2) {
						avgCoh.add(boid.getPos());
						cohCount++;
					}else {
						avgSep.add(boid.getPos());
						sepCount++;
					}
				}
			}	
		}
		
		if(aliCount != 0){
			aliAngle = avgAli/ aliCount;
			aliAngle -= this.direction;
			aliAngle *= this.aliStr;
			avgCount++;
		}
		if(cohCount != 0){
			avgCoh.divide(cohCount);
			cohAngle = Tools.angle(this.pos, avgCoh);
			cohAngle -= this.direction;
			cohAngle = Tools.wrapAngle(cohAngle);
			cohAngle *= this.cohStr;
			avgCount++;
		}
		if(sepCount != 0){
			avgSep.divide(sepCount);
			sepAngle = Tools.angle(this.pos, avgCoh);
			sepAngle -= this.direction;
			sepAngle = Tools.wrapAngle(sepAngle);
			sepAngle *= this.sepStr;
			avgCount++;
		}
		
		if(avgCount != 0) {
			return (aliAngle + cohAngle + sepAngle)/avgCount;	//Angle are averaged 
														//Bird set to turn
		}else {
			return 0;
		}
		
		
	}
	
	public void setAlignmentStr(double value) {
		this.aliStr = value;
	}
	public void setCohesionStr(int value) {
		this.cohStr = value;
		
	}
	public void setSeparationStr(int value) {
		this.sepStr = value;
		
	}
	

}
