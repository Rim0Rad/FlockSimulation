package tools;

import java.awt.Color;

public class HSBColor{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color color;
	private float H, S, B;
	
	public HSBColor(float H, float S, float B) {
		
		this.H = H;
		this.S = S;
		this.B = B;
		
		color = Color.getHSBColor(H, S, B);
	}

	public Color getColor() {
		return color;
	}

	public int getHue() {
		return (int) (H * 100);
	}

	public void setHue(int value) {
		
		this.H = (float) value/100;
		this.color = Color.getHSBColor(H, S, B);
		
	}

}
