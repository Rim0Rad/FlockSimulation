package boid;

import java.awt.Color;
import java.awt.Graphics;

public class Boid {

	private int boidLenght = 15;
	private int boidWidth = 5;
	
	private int x, y;
	private double x2, x3, y2, y3;
	
	private Color color;
	private int direction;
	
	
	public Boid(){
		this.x = 0;
		this.y = 0;
		this.color = Color.black;
		this.direction = 0;
		
		x2 = -boidLenght;
		x3 = -boidLenght;
		y2 = - boidWidth;
		y3 = boidWidth;
	}
	
	
	public void tick() {
		x++;
		y++;
		direction++;
		System.out.println("direction:" + direction);
		
		//System.out.println((x3 * Math.cos(direction) + y3 * -Math.sin(direction)));
		double radiants = Math.toRadians(direction);
		
		System.out.println("direction:" +( Math.cos(radiants) + y2 * Math.sin(radiants)));
		x2 =  (x2 * Math.cos(radiants) + y2 * Math.sin(radiants));
		y2 =  (x2 * -Math.sin(radiants) + y2 * Math.cos(radiants));
		
		x3 =  (x3 * Math.cos(radiants) + y3 * Math.sin(radiants));
		y3 =  (x3 * -Math.sin(radiants) + y3 * Math.cos(radiants));
		
		System.out.println(" "+ x2 + " " +  x3 + " " + y2+ " " + y3);
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.drawPolygon(new int[] {x , (int) (x + x2), (int) (x + x3)}, new int[] {y, (int) (y + y2), (int) (y + y3)}, 3);
	}
}
