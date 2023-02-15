package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boid.BoidB;

public class Handler {
	
	
	private List<List<BoidB>> flocks;
	private List<BoidB> boids;
	private int selectedFlock;
	
	/* Handles simulation objects */
	public Handler() {
		
		flocks = Collections.synchronizedList(new ArrayList<List<BoidB>>());
		boids =  Collections.synchronizedList(new ArrayList<BoidB>());
		flocks.add(boids);
		
		selectedFlock = 0;
	}

	public void tick() {
		synchronized(flocks){
			for(List<BoidB> boids: flocks) {
				System.out.println("tick flock");
				synchronized(boids){
					System.out.println("pre boid tick");
					for(BoidB boid : boids) {
						System.out.println("tick boid");
						boid.tick(boid.flock(boids));
					}
				}
			}
		}
	}

	public synchronized void render(Graphics g) {
		synchronized(flocks){
			for(List<BoidB> boids: flocks) {
				System.out.println("render flock" + flocks.indexOf(boids));
				synchronized(boids) {
					System.out.println("preboid render");
					for(BoidB boid : boids) {
						System.out.println("render a boid");
						boid.render(g);
					}
				}
			}
		}	
	}

	public List<BoidB> getBoids() {
		return flocks.get(selectedFlock);
	}

	public void updateAlignment(double value) {
		for(BoidB boid: flocks.get(selectedFlock)) {
			boid.setAlignmentStr(value);
		}
	}

	public void updateCohesion(double value) {
		for(BoidB boid: flocks.get(selectedFlock)) {
			boid.setCohesionStr(value);
		}
	}
	public void updateSeparation(double value) {
		for(BoidB boid: flocks.get(selectedFlock)) {
			boid.setSeparationStr(value);
		}
	}

	public void updateColor(Color value) {
		for(BoidB boid: flocks.get(selectedFlock)) {
			boid.setColor(value);
		}
		
	}

	public List<List<BoidB>> getFlocks() {
		return flocks;
	}

	public void flockSelected(int selectedIndex) {
		this.selectedFlock = selectedIndex;
	}
	public int getFlockSelected() {
		return selectedFlock;
	}

}
