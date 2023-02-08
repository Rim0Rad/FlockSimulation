package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boid.Boid;

public class Handler {
	
	private List<Boid> boids;
	
	public Handler() {
		boids =  Collections.synchronizedList(new ArrayList<Boid>());
		
	}

	public void tick() {
		synchronized(boids){
			for(Boid boid : boids) {
				boid.tick();
			}
		}
		
	}

	public void render(Graphics g) {
		synchronized(boids){
			for(Boid boid : boids) {
				boid.render(g);
			}
		}
		
	}

	public List<Boid> getBoids() {
		return boids;
	}

}
