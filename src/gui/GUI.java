package gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
	
	private int botPanHeight = 150;
	
	private JFrame frame;
	private JFrame frameFull = new JFrame();
	private boolean fullscreen = false;
	private int FRAME_WIDTH;
	private int FRAME_HEIGTH;
	
	
	private Canvas canvas;
	private int canvasWidth, canvasHeight;
	
	private Handler handler;
	
	private JSlider alignmentSlider;
	private JSlider cohesionSlider;
	private JSlider separationSlider;
	
	private JButton fullScreenBT;
	private BotomPanel botPanel;
	
	
	private JComboBox<String> flockSelectCB; 
	
	public GUI(Canvas canvas, Handler handler, Window window){
		
		frame = window;
		updateCanvas(frame);
		
		frameFull = new Window(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT, window.getName(), true);
		
		this.canvas = canvas;
		this.canvas.setSize(getCanvasWidth(), getCanvasHeigth());
		
		canvas.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_ESCAPE && fullscreen == true) {
					fullScreenBT.doClick();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			
		});
		
		this.handler = handler;
		botPanel  = new BotomPanel();
		
		frame.add(canvas, BorderLayout.CENTER);
		frame.add(botPanel, BorderLayout.SOUTH);
		
		frame.revalidate();
	}
	
	public void updateCanvas(JFrame frame) {
		System.out.println(frame.getHeight());
		if(fullscreen) {
			canvasHeight = frame.getHeight();
			canvasWidth	= frame.getWidth();
		}else {
			canvasHeight = frame.getContentPane().getHeight();
			canvasWidth	= frame.getContentPane().getWidth();
		}
		
	}
	public int getCanvasWidth() {
		return canvasWidth;
	}
	public int getCanvasHeigth() {
		return canvasHeight;
	}

	public class BotomPanel extends JPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public BotomPanel(){
			
			//this.setSize(0, botPanHeight);
			//this.setPreferredSize(new Dimension(0, botPanHeight));
			this.setBackground(new Color(100, 120, 180));
			
            JButton addBoidBT = new JButton("Add Boid");
            addBoidBT.setBackground(new Color(100, 120, 250));
            addBoidBT.setForeground(new Color(250,250,250));
            addBoidBT.setFocusPainted(false);
            addBoidBT.addActionListener(new ActionListener(){
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
            add(addBoidBT);
            
            JButton addFlockBT = new JButton("Add Flock");
            addFlockBT.setBackground(new Color(100, 120, 250));
            addFlockBT.setForeground(new Color(250,250,250));
            addFlockBT.setFocusPainted(false);
            addFlockBT.addActionListener(new ActionListener(){
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				synchronized(handler.getFlocks()){
    					handler.getFlocks().add(new ArrayList<BoidB>());
    					flockSelectCB.addItem("Flock " + handler.getFlocks().size());
    					flockSelectCB.setSelectedItem("Flock " + handler.getFlocks().size());
    				}
    				System.out.println("Flock added");
    			}
            });
            add(addFlockBT); 
            
            flockSelectCB = new JComboBox<String>();
            flockSelectCB.addItem("Flock " + handler.getFlocks().size());
            flockSelectCB.setEditable(false);
            flockSelectCB.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					handler.flockSelected(flockSelectCB.getSelectedIndex());
				}
            });
            add(flockSelectCB);
            
            
            //TODO: adding new boids should set their flocking parameters to current slider values
            TitledBorder alignmentBorder = new TitledBorder("Alignment");
            alignmentSlider = jSliderSetup(0, 0 , 100, 0, 10, 5, alignmentBorder, new Color(100, 120, 250));
            alignmentSlider.setPaintLabels(false);
            alignmentSlider.setPaintTicks(false);
            alignmentSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateAlignment(alignmentSlider.getValue() * 0.01);
				}
            });
            add(alignmentSlider);
            
            TitledBorder cohesionBorder = new TitledBorder("Cohesion");
            cohesionSlider = jSliderSetup(0, 0 , 100, 0, 10, 5, cohesionBorder, new Color(100, 120, 250));
            cohesionSlider.setPaintLabels(false);
            cohesionSlider.setPaintTicks(false);
            cohesionSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateCohesion(cohesionSlider.getValue() * 0.01);
				}
            });
            add(cohesionSlider);
            
            TitledBorder separationBorder = new TitledBorder("Separation");
            separationSlider = jSliderSetup(0, 0 , 100, 0, 10, 5, separationBorder, new Color(100, 120, 250));
            separationSlider .setPaintLabels(false);
            separationSlider.setPaintTicks(false);
            separationSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateSeparation(separationSlider.getValue() * 0.01);
				}
            });
            add(separationSlider);
            
            TitledBorder colorBorder = new TitledBorder("Color");
            JSlider colorSlider = jSliderSetup(0, 0 , 100, 0, 1, 0, colorBorder, new Color(100, 120, 250));
            colorSlider .setPaintLabels(false);
            colorSlider.setPaintTicks(false);
            colorSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateColor(Color.getHSBColor(((float) colorSlider.getValue()/100), 1, 1));
				}
            });
            add(colorSlider);
            
            
            
            fullScreenBT = new JButton("Fullscreen");
            fullScreenBT.setBackground(new Color(100, 120, 250));
            fullScreenBT.setForeground(new Color(250,250,250));
            fullScreenBT.setFocusPainted(false);
            fullScreenBT.addActionListener(new ActionListener(){
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				synchronized(handler.getFlocks()){
    					
    					if(!fullscreen) {
    						fullscreen = true;
    						frameFull = new Window(0, 0, "", true);
    						
    						frameFull.setExtendedState(JFrame.MAXIMIZED_BOTH);
    						frameFull.setVisible(true);
    						frameFull.add(frame.getContentPane());
    						
    						updateCanvas(frameFull);
    						
    						System.out.println(getCanvasWidth()+ " " + getCanvasHeigth());
    						
    						canvas.setSize(getCanvasWidth(), getCanvasHeigth());
    						canvas.setPreferredSize(new Dimension(getCanvasWidth(), getCanvasHeigth()));
    						
    						System.out.println(canvas.getWidth() + " " + canvas.getHeight());
    						
    						//set content pane invisilble
    						botPanel.setVisible(false);
    						//botPanel.setSize(0,0);


    						FRAME_WIDTH = frame.getWidth();
    						FRAME_HEIGTH = frame.getHeight();
    						
    						frame.dispose();
    						
    						
    						
    						
    					}else {
    						
    						
    						frame = new Window(FRAME_WIDTH, FRAME_HEIGTH,"hello");
    						frame.add(frameFull.getContentPane());
    						frame.setVisible(true);
    						updateCanvas(frame);
    						
    						canvas.setSize(frame.getPreferredSize());
    						
    						botPanel.setVisible(true);
    						
    						frameFull.dispose();

    						fullscreen = false;
    					}
    				}
    			}

				
            });
            add(fullScreenBT);
            
            
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

	public void update(int width, int heigth) {
		this.canvas.setSize(new Dimension(width, heigth));
		
	}
	
	
	
	
}
