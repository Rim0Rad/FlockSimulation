package tools;


/* Triangle that is relative to x = 0 y = 0 
 * Point data used to */
public class Triangle {

	private Coordinates2D p1, p2, p3;
	private int length, width;
	
	public Triangle(int length, int width, double direction) {
		
		this.length = length;
		this.width = width;
		
		p1 = new Coordinates2D(5, 0);
		p2 = new Coordinates2D(-length, -width);
		p3 = new Coordinates2D(-length, width);
		
	}
	
	public void Rotate(double direction) {
		double radiants = Math.toRadians(-direction);
		p1.setX(length * Math.cos(radiants) + (-0) * Math.sin(radiants));
		p1.setY(length * -Math.sin(radiants) + (-0) * Math.cos(radiants));
		
		p2.setX(-length * Math.cos(radiants) + (-width) * Math.sin(radiants));
		p2.setY(-length * -Math.sin(radiants) + (-width) * Math.cos(radiants));
		
		p3.setX(-length * Math.cos(radiants) + width * Math.sin(radiants));
		p3.setY(-length * -Math.sin(radiants) + width * Math.cos(radiants));
	}
	
	public int[] relativeToX(int x) {
		return new int[] {(int) (x + p1.getX()), (int) (x + p2.getX()), (int) (x + p3.getX())};
	}
	public int[] relativeToY(int y){
		return new int[] {(int) (y + p1.getY()), (int) (y + p2.getY()), (int) (y + p3.getY())};
	}
	
}
