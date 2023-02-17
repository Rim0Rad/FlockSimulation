package gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

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
import tools.HSBColor;

public class GUI {
	
	private int botPanHeight = 50;
	
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
	private JSlider colorSlider;
	
	private JButton fullScreenBT;
	private BotomPanel botPanel;
	
	/* Colors */
	private Color btBackgrounClr = new Color(100, 120, 250);
	private Color btForegrounClr = new Color(250, 250, 250);
	
	private JComboBox<String> flockSelectCB; 
	
	public GUI(Canvas canvas, Handler handler, Window window){
		
		frame = window;
		frameFull = new Window(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT, window.getName(), true);
		this.handler = handler;
		this.canvas = canvas;
		this.canvas.addKeyListener(new KeyListener() {
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
		updateCanvas(frame);
		
		botPanel  = new BotomPanel();
		
		frame.add(canvas, BorderLayout.CENTER);
		frame.add(botPanel, BorderLayout.SOUTH);
		
		frame.revalidate();
	}
	
	public void updateCanvas(JFrame frame) {
		if(fullscreen) {
			canvasHeight = frame.getHeight();
			canvasWidth	= frame.getWidth();
		}else{
			canvasHeight = frame.getContentPane().getHeight() - botPanHeight;
			canvasWidth	= frame.getContentPane().getWidth();
		}
	}
	
	public int getCanvasWidth() {return canvasWidth;}
	public int getCanvasHeigth() {return canvasHeight;}

	public class BotomPanel extends JPanel{
		private static final long serialVersionUID = 1L;

		public BotomPanel(){
			
			this.setPreferredSize(new Dimension(0, botPanHeight));
			this.setBackground(new Color(100, 120, 180));
			
            JButton addBoidBT = newButton("Add Boid", btBackgrounClr, btForegrounClr);
            addBoidBT.addActionListener(new ActionListener(){
    			@Override
    			public void actionPerformed(ActionEvent e) {
    					for(int i = 0; i < 10; i++) {
    						
    						BoidB boid = new BoidB();
    						System.out.println(colorSlider.getValue());
    						boid.setColor(colorSlider.getValue());
    						boid.updateParameters(alignmentSlider.getValue(), cohesionSlider.getValue(),separationSlider.getValue());
    						
    						handler.addBoid(boid);
    					}
    			}
            });
            add(addBoidBT);
            
            JButton addFlockBT = newButton("Add Flock", btBackgrounClr, btForegrounClr);
            addFlockBT.addActionListener(new ActionListener(){
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				synchronized(handler.getFlocks()){
    					handler.getFlocks().add(Collections.synchronizedList(new ArrayList<BoidB>()));
    					flockSelectCB.addItem("Flock " + handler.getFlocks().size());
    					flockSelectCB.setSelectedItem("Flock " + handler.getFlocks().size());
    				}
    			}
            });
            add(addFlockBT); 
            
            flockSelectCB = new JComboBox<String>();
            flockSelectCB.addItem("Flock " + handler.getFlocks().size());
            flockSelectCB.setEditable(false);
            flockSelectCB.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					handler.setFlockSelected(flockSelectCB.getSelectedIndex());
					resetFlockSlider();
				}
				private void resetFlockSlider() {
					alignmentSlider.setValue(handler.getAlignment());
					cohesionSlider.setValue(handler.getCohesion());
					separationSlider.setValue(handler.getSeparation());
				}
            });
            add(flockSelectCB);
            
            alignmentSlider = jSliderSetup(0, 0 , 100, 0, 10, 5, "Alignment", new Color(100, 120, 250)); 
            alignmentSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateAlignment(alignmentSlider.getValue());
				}
            });
            add(alignmentSlider);
            
            cohesionSlider = jSliderSetup(0, 0 , 100, 0, 10, 5, "Cohesion", new Color(100, 120, 250));
            cohesionSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateCohesion(cohesionSlider.getValue());
				}
            });
            add(cohesionSlider);
            
            separationSlider = jSliderSetup(0, 0 , 100, 0, 10, 5, "Separation", new Color(100, 120, 250));
            separationSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateSeparation(separationSlider.getValue());
				}
            });
            add(separationSlider);
            
            colorSlider = jSliderSetup(0, 0 , 100, 40, 1, 0, "Color", new Color(100, 120, 250));
            colorSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateColor(new HSBColor((float) colorSlider.getValue()/100, 1 , 1));
							//Color.getHSBColor((, 1, 1));
				}
            });
            add(colorSlider);
            
            fullScreenBT = newButton("Fullscreen", btBackgrounClr,  btForegrounClr);
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
    						canvas.requestFocus();
    						canvas.setSize(getCanvasWidth(), getCanvasHeigth());
    						//canvas.setPreferredSize(new Dimension(getCanvasWidth(), getCanvasHeigth()));
    						
    						botPanel.setVisible(false);

    						FRAME_WIDTH = frame.getWidth();
    						FRAME_HEIGTH = frame.getHeight();
    						
    						frame.dispose();
    						
    					}else {
    						
    						fullscreen = false;
    						frame = new Window(FRAME_WIDTH, FRAME_HEIGTH,"Flocking Simulator");
    						frame.add(frameFull.getContentPane());
    						frame.setVisible(true);
    						updateCanvas(frame);
    						botPanel.setVisible(true);
    						
    						frameFull.dispose();

    					}
    				}
    			}	
            });
            add(fullScreenBT);
		}
	}
	
	/* Slider setup */
	private JSlider jSliderSetup(int alignment, int minVal, int maxVal, int startVal, int majTick, int minTick, String title, Color backColor){
		JSlider slider = new JSlider(alignment, minVal, maxVal, startVal);
		slider.setMajorTickSpacing(majTick);
		slider.setMinorTickSpacing(minTick);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		slider.setPaintLabels(false);
		slider.setPaintTicks(false);
		slider.setBorder(new TitledBorder(title));
		slider.setBackground(backColor);
		return slider;
	}
	/*Button setup */
	public JButton newButton(String title, Color background, Color foreground) {
		JButton button = new JButton(title);
		button.setBackground(background);
		button.setForeground(foreground);
		button.setFocusPainted(false);
		return button;
	}
}
