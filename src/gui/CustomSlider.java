package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

public class CustomSlider extends JSlider{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5108245515809022232L;
	private Font font = new Font(null, 0, 10);
	
	/* Slider setup */
	CustomSlider(int alignment, int minVal, int maxVal, int startVal, int majTick, int minTick, String title, Color backColor, Color frontColor){
		super(alignment, minVal, maxVal, startVal);
		
		setMajorTickSpacing(majTick);
		setMinorTickSpacing(minTick);
		setBackground(backColor);
		
		setPaintLabels(true);
		setPaintTicks(true);
		setSnapToTicks(true);
		setPaintLabels(false);
		setPaintTicks(false);
		
		
		TitledBorder border  = new TitledBorder(title);
		border.setTitleFont(font);
		border.setTitleColor(frontColor);
		setBorder(border);
		
	}
	
	@Override
	public void updateUI() {
        setUI(new CustomSliderUI(this));
    }
	
}
