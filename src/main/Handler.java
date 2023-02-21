package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boid.BoidF;
import tools.HSBColor;

public class Handler {
	
	private List<List<BoidF>> flocks;
	private List<BoidF> boids;
	private int selectedFlock;
	
	/* Handles simulation objects */
	public Handler() {
		flocks = Collections.synchronizedList(new ArrayList<List<BoidF>>());
		boids =  Collections.synchronizedList(new ArrayList<BoidF>());
		flocks.add(boids);
		selectedFlock = 0;
	}

	/* Run boid behaviour calculations */
	public synchronized void tick() {
		synchronized(flocks){
			for(List<BoidF> boids: flocks) {
				synchronized(boids){
					for(BoidF boid : boids) {
						boid.tick(boid.flock(boids));
					}
				}
			}
		}
	}

	/* Draw boids */
	public void render(Graphics g) {
		synchronized(flocks){
			for(List<BoidF> boids: flocks) {
				synchronized(boids) {
					for(BoidF boid : boids) {
						boid.render(g);
					}
				}
			}
		}	
	}

	/*TODO: if the problem with synchronized freezing appears again, could add boids to a separate array and add them at the end of tick */
	public void addBoid(BoidF boid) {
		flocks.get(selectedFlock).add(boid);
	}
	
	public void updateAlignment(double value) {
		for(BoidF boid: flocks.get(selectedFlock)) {
			boid.setAlignmentStr(value * 0.01);
		}
	}

	public void updateCohesion(double value) {
		for(BoidF boid: flocks.get(selectedFlock)) {
			boid.setCohesionStr(value* 0.01);
		}
	}
	
	public void updateSeparation(double value) {
		for(BoidF boid: flocks.get(selectedFlock)) {
			boid.setSeparationStr(value* 0.01);
		}
	}

	public void updateColor(HSBColor hsbColor) {
		for(BoidF boid: flocks.get(selectedFlock)) {
			boid.setColor(hsbColor);
		}
	}
	
	public void updateSize(int size) {
		for(BoidF boid: flocks.get(selectedFlock)) {
			boid.setSize(size);
		}
	}

	public List<List<BoidF>> getFlocks() {return flocks;}

	public void setFlockSelected(int selectedIndex) {
		this.selectedFlock = selectedIndex;
		}
	
	public int getFlockSelected() {return selectedFlock;}

	/* Get the alignment strength of the currently selected flock */
	public int getAlignment() {
			if(flocks.get(selectedFlock).isEmpty()) {
				return 0;
			}else {
				return flocks.get(selectedFlock).get(0).getAlignment();
			}
	}
	/* Get the cohesion strength of the currently selected flock */
	public int getCohesion() {
		if(flocks.get(selectedFlock).size() == 0) {
			return 0;
		}else {
			return flocks.get(selectedFlock).get(0).getCohesion();
		}
	}
	/* Get the separation strength of the currently selected flock */
	public int getSeparation() {
		if(flocks.get(selectedFlock).size() == 0) {
			return 0;
		}else {
			return flocks.get(selectedFlock).get(0).getSeparation();
		}
	}

	/* Get colors hue of selected floc */
	public int getColorValue() {
		if(flocks.get(selectedFlock).size() == 0) {
			return 0;
		}else {
			return flocks.get(selectedFlock).get(0).getColor();
		}
	}

	/* Remove all of the boids and flocks from simulation */
	public void clear() {
		System.out.println(flocks);
		synchronized(flocks) {
			flocks.removeAll(flocks);
			boids.removeAll(boids);
			flocks.add(boids);
			selectedFlock = 0;
		}	
	}

	public int getSize() {
		if(flocks.get(selectedFlock).size() == 0) {
			return 0;
		}else {
			return flocks.get(selectedFlock).get(0).getSize();
		}
	}

	public void updateSpeed(int value) {
		for(BoidF boid: flocks.get(selectedFlock)) {
			boid.setSpeed(value);
		}
		
	}

	public void removeFlock(int selectedIndex) {
		flocks.remove(selectedIndex);
		selectedFlock--;
		
	}

	

	
}
