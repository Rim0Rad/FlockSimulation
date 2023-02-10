package tools;

public class Tools {

	/* Calculate distance between two points */
	public static double distance(Coordinates2D pos1, Coordinates2D pos2) {
		double deltaX = pos2.getX() - pos1.getX();
		double deltaY = pos2.getY() - pos1.getY();
		return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
	}

	
	/* Wraps given angle within -180 - 180 degree range */
	public static double wrapAngle(double angle) {
		while(angle > 180 || angle <-180) {
			if(angle > 180) {
				angle = angle - 360 ;
			}
			else if(angle < -180) {
				angle = angle + 360;
			}
		}
		return angle;
	}
	
	// Returns true if points are within radius distance of each other 
	public static boolean withinRange(Coordinates2D pos1, Coordinates2D pos2, int radius ) {
		if(distance(pos1, pos2) <= radius) {
			return true;
		}
		return false;
	}
}
