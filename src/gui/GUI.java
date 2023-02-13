package gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import boid.BoidB;
import main.Handler;
import main.Window;

public class GUI {
	
	private JFrame frame;
	//private Canvas canvas;
	
	Handler handler;
	
	private JSlider alignmentSlider;
	private JSlider cohesionSlider;
	private JSlider separationSlider;
	
	public GUI(Canvas canvas, Handler handler, Window window){
		
		//this.canvas = canvas;
		this.handler = handler;
		
		frame = window;
		frame.add(new BotomPanel(), BorderLayout.SOUTH);
		frame.add(canvas, BorderLayout.CENTER);
		
		frame.revalidate();
	}
	
	public class BotomPanel extends JPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public BotomPanel(){
			
			this.setPreferredSize(new Dimension(0, 35));
			this.setBackground(new Color(100, 120, 180));
			
            JButton btn = new JButton("Add Boid");
            btn.setBackground(new Color(100, 120, 250));
            btn.setForeground(new Color(250,250,250));
            btn.setFocusPainted(false);
            btn.addActionListener(new ActionListener(){
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				synchronized(handler.getBoids()){
    					for(int i = 0; i < 10; i++) {
    						
    						handler.getBoids().add(new BoidB());
    					}
    				}
    				System.out.println("Boid added");
    			}
            });
            add(btn);

            
            //TODO: adding new boids should set their flocking parameters to current slider values
            
            TitledBorder alignmentBorder = new TitledBorder("Alignment");
            alignmentSlider = jSliderSetup(0, 0 , 100, 0, 10, 5, alignmentBorder, new Color(100, 120, 250));
            alignmentSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateAlignment(alignmentSlider.getValue() * 0.01);
				}
            });
            add(alignmentSlider);
            
            TitledBorder cohesionBorder = new TitledBorder("Cohesion");
            cohesionSlider = jSliderSetup(0, 0 , 100, 0, 10, 5, cohesionBorder, new Color(100, 120, 250));
            cohesionSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateCohesion(cohesionSlider.getValue() * 0.01);
				}
            });
            add(cohesionSlider);
            
            TitledBorder separationBorder = new TitledBorder("Separation");
            separationSlider = jSliderSetup(0, 0 , 100, 0, 10, 5, separationBorder, new Color(100, 120, 250));
            separationSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateSeparation(separationSlider.getValue() * 0.01);
				}
            });
            add(separationSlider);
            
            
            
		}
	}
	
	
	//This method set up a JSlider
	private JSlider jSliderSetup(int alignment, int minVal, int maxVal, int startVal, int majTick, int minTick, TitledBorder border, Color backColor){
		JSlider newSlider = new JSlider(alignment, minVal, maxVal, startVal);
		newSlider.setMajorTickSpacing(majTick);
		newSlider.setMinorTickSpacing(minTick);
		newSlider.setPaintLabels(true);
		newSlider.setPaintTicks(true);
		newSlider.setSnapToTicks(true);
		newSlider.setBorder(border);
		newSlider.setBackground(backColor);
		return newSlider;
	}
	
	public double getAlignment() {
		return alignmentSlider.getValue();
	}
	
}
