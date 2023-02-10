package tools;

public class Coordinates2D {
	private double x, y;
	
	public Coordinates2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return (int) x;
	}
	public int getY() {
		return (int) y;
	}

	public void add(int x, int y) {
		this.x += x;
		this.y += y;
		
	}
	
	public void add(Coordinates2D pos) {
		this.x += pos.getX();
		this.y += pos.getY();
		
	}

	public void add(double x, double y) {
		// TODO Auto-generated method stub
		this.x += x;
		this.y += y;
	}

	public void subtract(Coordinates2D pos) {
		this.x -= pos.getX();
		this.y -= pos.getY();
	}

	public void divide(int div) {
		this.x /= div;
		this.y /= div;
		
	}
	
	
}
