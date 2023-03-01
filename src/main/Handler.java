package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boid.BoidF;
import tools.HSBColor;

public class Handler {
	
	private List<List<BoidF>> flocks;
	private int selectedFlock = 0;
	
	/* Handles simulation objects */
	public Handler() {
		flocks = Collections.synchronizedList(new ArrayList<List<BoidF>>());
		addFlock();
	}
	
	/* Removes a flock at specified index */
	public void removeFlock(int index) {
		flocks.remove(index);
	}

	/* Add new flock to simulation */
	public void addFlock() {
		flocks.add(Collections.synchronizedList(new ArrayList<BoidF>()));
	}

	/*TODO: if the problem with synchronised freezing appears again, could add boids to a separate array and add them at the end of tick */
	public void addBoid(BoidF boid) {
		flocks.get(selectedFlock).add(boid);
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

	/* Updates the alignment strength of all boids in the selected flock */
	public void updateAlignment(double value) {
		for(BoidF boid: flocks.get(selectedFlock)) {
			boid.setAlignmentStr(value * 0.01);
		}
	}
	
	/* Updates the cohesion strength of all boids in the selected flock */
	public void updateCohesion(double value) {
		for(BoidF boid: flocks.get(selectedFlock)) {
			boid.setCohesionStr(value* 0.01);
		}
	}
	
	/* Updates the separation strength of all boids in the selected flock */
	public void updateSeparation(double value) {
		for(BoidF boid: flocks.get(selectedFlock)) {
			boid.setSeparationStr(value* 0.01);
		}
	}

	/* Updates the colour hue of all boids in the selected flock */
	public void updateColor(int hueValue) {
		HSBColor color = new HSBColor((float) hueValue/100, 1 , 1);
		for(BoidF boid: flocks.get(selectedFlock)) {
			boid.setColor(color);
		}
	}
	
	/* Updates the size of all boids in the selected flock */
	public void updateSize(int size) {
		for(BoidF boid: flocks.get(selectedFlock)) {
			boid.setSize(size);
		}
	}

	/* Updates the speed of all boids in the selected flock */
	public void updateSpeed(int value) {
		
		for(BoidF boid: flocks.get(selectedFlock)) {
			boid.setSpeed(value);
		}
	}
	
	/* Remove all boids and flocks from simulation */
	public void clear() {
		
		synchronized(flocks) {
			flocks.removeAll(flocks);
			addFlock();
			selectedFlock = 0;
		}	
	}


	/**********************
	 * 
	 * Getters and setters 
	 *
	 ***********************/
	/* Get the alignment strength of the currently selected flock */
	public int getAlignment(int selectedFlock) {
		return (flocks.get(selectedFlock).isEmpty()) ?  0 : flocks.get(selectedFlock).get(0).getAlignment();
	}
	/* Get the cohesion strength of the currently selected flock */
	public int getCohesion() {
		return (flocks.get(selectedFlock).isEmpty()) ?  0 : flocks.get(selectedFlock).get(0).getCohesion();
	}
	/* Get the separation strength of the currently selected flock */
	public int getSeparation() {
		return (flocks.get(selectedFlock).isEmpty()) ?  0 : flocks.get(selectedFlock).get(0).getSeparation();
	}
	/* Get boid's size of the currently selected flock */
	public int getBoidSize() {
		return (flocks.get(selectedFlock).isEmpty()) ?  0 : flocks.get(selectedFlock).get(0).getSize();
	}
	
	/* Get boid's colour hue of the currently selected flock */
	public int getColorValue() {
		return (flocks.get(selectedFlock).isEmpty()) ?  0 : flocks.get(selectedFlock).get(0).getColor();
	}
	
	/* Return the number of flocks */
	public int getFlockCount() {return flocks.size();}
	
	public void setSelectedFlock(int selectedIndex) {this.selectedFlock = selectedIndex;}
	
	public int getSelectedFlock() {return selectedFlock;}

}
