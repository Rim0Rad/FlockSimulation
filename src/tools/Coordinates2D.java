package tools;

public class Coordinates2D {
	
	private double x, y;
	
	public Coordinates2D(int x, int y) {
		this.x = x;
		this.y = y;
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
	
	public void wrap(int maxX, int maxY) {
		if( this.x > maxX+5){
			this.x = 0;
		}
		else if( this.x  < -5){
			this.x = maxX;
		}
		else if(this.y  > maxY+5){
			this.y = 0;
		}
		else if( this.y < -5){
			this.y = maxY;
		}
	}
	
	public int getX() {
		return (int) x;
	}
	public int getY() {
		return (int) y;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void wrap() {
		// TODO Auto-generated method stub
		
	}
	
}
