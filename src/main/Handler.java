package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boid.BoidB;
import tools.HSBColor;

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

	public synchronized void tick() {
		synchronized(flocks){
			for(List<BoidB> boids: flocks) {
				synchronized(boids){
					for(BoidB boid : boids) {
						boid.tick(boid.flock(boids));
					}
				}
			}
		}
	}

	public void render(Graphics g) {
		synchronized(flocks){
			for(List<BoidB> boids: flocks) {
				synchronized(boids) {
					for(BoidB boid : boids) {
						boid.render(g);
					}
				}
			}
		}	
	}

	
	public void addBoid(BoidB boid) {
		flocks.get(selectedFlock).add(boid);
	}
	
	public void updateAlignment(double value) {
		for(BoidB boid: flocks.get(selectedFlock)) {
			boid.setAlignmentStr(value * 0.01);
		}
	}

	public void updateCohesion(double value) {
		for(BoidB boid: flocks.get(selectedFlock)) {
			boid.setCohesionStr(value* 0.01);
		}
	}
	public void updateSeparation(double value) {
		for(BoidB boid: flocks.get(selectedFlock)) {
			boid.setSeparationStr(value* 0.01);
		}
	}

	public void updateColor(HSBColor hsbColor) {
		for(BoidB boid: flocks.get(selectedFlock)) {
			boid.setColor(hsbColor);
		}
		
	}

	public List<List<BoidB>> getFlocks() {
		return flocks;
	}

	public void setFlockSelected(int selectedIndex) {
		this.selectedFlock = selectedIndex;
	}
	public int getFlockSelected() {
		return selectedFlock;
	}

	public int getAlignment() {
		if(flocks.get(selectedFlock).size() == 0) {
			return 0;
		}else {
			return flocks.get(selectedFlock).get(0).getAlignment();
		}
	}
	public int getCohesion() {
		if(flocks.get(selectedFlock).size() == 0) {
			return 0;
		}else {
			return flocks.get(selectedFlock).get(0).getCohesion();
		}
	}
	public int getSeparation() {
		if(flocks.get(selectedFlock).size() == 0) {
			return 0;
		}else {
			return flocks.get(selectedFlock).get(0).getSeparation();
		}
	}

	public int getColorValue() {
		if(flocks.get(selectedFlock).size() == 0) {
			return 0;
		}else {
			return flocks.get(selectedFlock).get(0).getColor();
		}
	}
}
