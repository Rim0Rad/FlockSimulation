package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boid.Boid;
import boid.BoidA;
import boid.BoidB;

public class Handler {
	
	private List<BoidB> boids;
	
	/* Handles simulation objects */
	public Handler() {
		boids =  Collections.synchronizedList(new ArrayList<BoidB>());
		
		/* Flock TEST */
		/*BoidB boid = new BoidB(100, 100, 135);
		boids.add(boid);
		boid = new BoidB(125, 125, -80);
		boids.add(boid);
		*/
	}

	public void tick() {
		synchronized(boids){
			for(BoidB boid : boids) {
				boid.tick(boid.flock(boids));
			}
		}
	}

	public void render(Graphics g) {
		synchronized(boids){
			for(BoidB boid : boids) {
				boid.render(g);
			}
		}	
	}

	public List<BoidB> getBoids() {
		return boids;
	}

	public void updateAlignment(double value) {
		for(BoidB boid: boids) {
			boid.setAlignmentStr(value);
		}
	}

	public void updateCohesion(double value) {
		for(BoidB boid: boids) {
			boid.setCohesionStr(value);
		}
	}
	public void updateSeparation(double value) {
		for(BoidB boid: boids) {
			boid.setSeparationStr(value);
		}
	}

}
