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
		while(angle > 180 || angle < -180) {
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
	
	
	public static double angleBetweenDirections(double curr, double target) {
		double angle = 0.0;
		
		if(curr > 90 && target < -90) {
			//angle = -((180 - curr) + (180 + target));
			angle = curr - target + 360;
		}else if(curr < -90 && target > 90) {
			angle = (180 + curr) + (180 - target);
			
		}else {
			angle = curr - target;
		}
		
		return angle;
	}
	
	public static double angle(Coordinates2D point1, Coordinates2D point2) {
		double xComp = point2.getX() - point1.getX();
		double yComp = point2.getY() - point1.getY();
		double angle = Math.atan2(yComp, xComp);
		angle = Math.toDegrees(angle);
		return angle;
	}


	
}
