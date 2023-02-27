package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;


import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import boid.BoidF;
import main.Handler;

import tools.HSBColor;

public class ControlPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1195023324794864670L;
	
	private CustomSlider alignmentSlider;
	private CustomSlider cohesionSlider;
	private CustomSlider separationSlider;
	private CustomSlider colorSlider;
	private CustomSlider sizeSlider;
	private CustomSlider speedSlider;
	private CustomButton fullScreenBT;
	private CustomComboBox<String> flockSelectCB; 
	private CustomCheckBox transferSettingCB;
	
	/*********************
	 * Component Colours 
	 *********************/
	//Buttons
	private Color btBackgrounClr = new Color(100, 120, 250);
	private Color btForegrounClr = new Color(250, 250, 250);
	//Sliders
	private Color sliderBackgroundClr = new Color(100, 120, 250);
	private Color sliderForegrounClr = new Color(250, 250, 250);
	
	private Handler handler;
	
	
	public ControlPanel(Dimension prefSize, Handler handler){
		
		this.handler = handler;
		
		this.setPreferredSize(prefSize);
		this.setBackground(new Color(100, 120, 180));
		
		CPanel flocksPanel = new CPanel();
		
		 /* Adds a new flock, updates combo box*/
		CustomButton addFlockBT = new CustomButton("Add Flock", btBackgrounClr, btForegrounClr);
        
        addFlockBT.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized(handler.getFlocks()){
					handler.getFlocks().add(Collections.synchronizedList(new ArrayList<BoidF>()));
					flockSelectCB.addItem("Flock " + handler.getFlocks().size());
					flockSelectCB.setSelectedItem("Flock " + handler.getFlocks().size());
				}
			}
        });
        /* When enabled, keeps the current flocks setting for new flock*/
        transferSettingCB = new CustomCheckBox("Save Settings");
        
        flocksPanel.add(Box.createRigidArea(new Dimension(50,10)));
        flocksPanel.add(addFlockBT);
        flocksPanel.add(Box.createRigidArea(new Dimension(0,10)));
        flocksPanel.add(transferSettingCB);
        add(flocksPanel);
        
		
		CPanel addPanel = new CPanel();
		/* ADD 1 BOID */
		CustomButton addBoidBT = new CustomButton("Add Boid", btBackgrounClr, btForegrounClr);
        addBoidBT.addActionListener(new ActionListener(){
        	
			@Override
			public void actionPerformed(ActionEvent e) {
					
				BoidF boid = new BoidF();
				boid.setColor(colorSlider.getValue());
				boid.updateParameters(alignmentSlider.getValue(), cohesionSlider.getValue(),separationSlider.getValue());
				boid.setSize(sizeSlider.getValue());
				boid.setSpeed(speedSlider.getValue());
				handler.addBoid(boid);
		
			}
        });
        /* ADD 10 BOIDS */
        CustomButton add10BoidBT = new CustomButton("Add 10", btBackgrounClr, btForegrounClr);
        add10BoidBT.addActionListener(new ActionListener(){
        	
			@Override
			public void actionPerformed(ActionEvent e) {
					for(int i = 0; i < 10; i++) {
						BoidF boid = new BoidF();
						boid.setColor(colorSlider.getValue());
						boid.updateParameters(alignmentSlider.getValue(), cohesionSlider.getValue(),separationSlider.getValue());
						boid.setSize(sizeSlider.getValue());
						boid.setSpeed(speedSlider.getValue());
						handler.addBoid(boid);
					}
			}
        });
        /*ADD 100 BOIDS */
        CustomButton add100BoidBT = new CustomButton("Add 100", btBackgrounClr, btForegrounClr);
        add100BoidBT.addActionListener(new ActionListener(){
        	
			@Override
			public void actionPerformed(ActionEvent e) {
					for(int i = 0; i < 100; i++) {
						BoidF boid = new BoidF();
						boid.setColor(colorSlider.getValue());
						boid.updateParameters(alignmentSlider.getValue(), cohesionSlider.getValue(),separationSlider.getValue());
						boid.setSize(sizeSlider.getValue());
						boid.setSpeed(speedSlider.getValue());
						handler.addBoid(boid);
					}
			}
        });
        
        addPanel.add(addBoidBT);
        addPanel.add(add10BoidBT);
        addPanel.add(add100BoidBT);
        add(addPanel);
        
        CPanel removePanel = new CPanel();
        
        /* Remove currently selected flock */
        CustomButton removeFlockBT = new CustomButton("Remove Flock",btBackgrounClr, btForegrounClr);
        removeFlockBT.addActionListener(BUTTON_REMOVE_FLOCK_ACTION_LISTENER);
        
        /* Remove all of the flocks and boids, and reset to initial state */
        CustomButton clearBT = new CustomButton("Clear",btBackgrounClr, btForegrounClr);
        clearBT.addActionListener(BUTTON_CLEAR_ACTION_LISTENER);
        
        removePanel.add(removeFlockBT);
        removePanel.add(clearBT);
        add(removePanel);
        
        
        flockSelectCB = new CustomComboBox<String>(handler.getFlockSelected());  
        flockSelectCB.addItemListener(COMBO_BOX_ITEM_LISTENER);
        add(flockSelectCB);
  
        /* Flocking Parameter panel */
        CPanel flockingPanel = new CPanel();
        
        alignmentSlider = new CustomSlider(0, 0 , 100, 0, 10, 5, "Alignment", sliderBackgroundClr, sliderForegrounClr); 
        alignmentSlider.addChangeListener(SLIDER_ALIGNMENT_CHANGE_LISTENER);
        
        cohesionSlider = new CustomSlider(0, 0 , 100, 0, 10, 5, "Cohesion", sliderBackgroundClr, sliderForegrounClr);
        cohesionSlider.addChangeListener(SLIDER_COHESION_CHANGE_LISTENER);
        
        separationSlider = new CustomSlider(0, 0 , 100, 0, 10, 5, "Separation", sliderBackgroundClr, sliderForegrounClr);
        separationSlider.addChangeListener(SLIDER_SEPARATION_CHANGE_LISTENER);
       
        flockingPanel.add(alignmentSlider);
        flockingPanel.add(cohesionSlider);
        flockingPanel.add(separationSlider);
        add(flockingPanel); 
        
        /* Panel for boid attributes: colour, size, speed*/
        CPanel atributesPanel = new CPanel();
        
        colorSlider = new CustomSlider(0, 0 , 100, 40, 1, 0, "Color", sliderBackgroundClr, sliderForegrounClr);
        colorSlider.addChangeListener(SLIDER_COLOR_CHANGE_LISTENER);
        
        sizeSlider = new CustomSlider(0, 0, 50, 10, 1, 1, "Size", sliderBackgroundClr,sliderForegrounClr);
        sizeSlider.addChangeListener(SLIDER_SIZE_CHANGE_LISTENER);
        
        /* Control boid's speed */
        speedSlider = new CustomSlider(0, 0, 20, 2, 1, 1, "Speed", sliderBackgroundClr,sliderForegrounClr);
        speedSlider.addChangeListener(SLIDER_SPEED_CHANGE_LISTENER);
       
        atributesPanel.add(colorSlider);
        atributesPanel.add(sizeSlider);
        atributesPanel.add(speedSlider);
        add(atributesPanel);
        
        
        /* Enables full screen mode:
         * Creates new full sized undecorated frame, transfer contents from windowed frame
         * and dispose of the old frame.*/
        
        fullScreenBT = new CustomButton("Fullscreen", btBackgrounClr,  btForegrounClr);
        //TODO: add action listener from GUI fullScreenBT.addActionListener() to enable fullscreen;
        
        add(fullScreenBT);
	}

	/***********************
	 * 
	 *  Getters and Setters
	 *  
	 ***********************/
	public boolean togleFulscreen(boolean fullscreen) {
		this.fullScreenBT.doClick();
		return (fullscreen) ? false: true;
			
	}
	
	/**************
	 * 
	 *  Listeners
	 *   
	 **************/
	
	/* SPEED - update */
	ChangeListener SLIDER_SPEED_CHANGE_LISTENER = new ChangeListener(){

		@Override
		public void stateChanged(ChangeEvent e) {
			handler.updateSpeed(speedSlider.getValue());
			
		}
    	
    };

    /* SIZE - update */
    ChangeListener SLIDER_SIZE_CHANGE_LISTENER = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {
			
			handler.updateSize(sizeSlider.getValue());
		}
    	
    };
   
    /* COLOR - update */
    ChangeListener SLIDER_COLOR_CHANGE_LISTENER = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {
			handler.updateColor(new HSBColor((float) colorSlider.getValue()/100, 1 , 1));

		}
    };
    
    /* ALIGNMENT - update */
    ChangeListener SLIDER_ALIGNMENT_CHANGE_LISTENER = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			handler.updateAlignment(alignmentSlider.getValue());
		}
    };
    
    /* COHESIONS - update */
    ChangeListener SLIDER_COHESION_CHANGE_LISTENER = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			handler.updateCohesion(cohesionSlider.getValue());
		}
    };
    
    /* SEPARATION SLIDER- update separations strength*/ 
    ChangeListener SLIDER_SEPARATION_CHANGE_LISTENER = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			handler.updateSeparation(separationSlider.getValue());
		}
    };
    
    /* FLOCK SELECT - select the current flock to control*/
    ItemListener COMBO_BOX_ITEM_LISTENER = new ItemListener() {
    	
    	
		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if(flockSelectCB.getItemCount() != 0) {
				handler.setFlockSelected(flockSelectCB.getSelectedIndex());
				
				if(!transferSettingCB.isSelected()) {
					resetFlockSliders(); 
				}
			}
			
		}
		
		/* Set slider values to the parameters of the current flock */
		private void resetFlockSliders() {
			
			alignmentSlider.setValue(handler.getAlignment());
			cohesionSlider.setValue(handler.getCohesion());
			separationSlider.setValue(handler.getSeparation());
			colorSlider.setValue(handler.getColorValue());
			sizeSlider.setValue(handler.getSize());
			
		}
    };
    
    /* CLEAR BOIDS/FLOCKS - removes all the boid and flocks from the simulation and resets "Flock select" combo box to initial state*/
    ActionListener BUTTON_CLEAR_ACTION_LISTENER = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			flockSelectCB.removeAllItems();
			handler.clear();
			handler.setFlockSelected(flockSelectCB.getSelectedIndex());
			flockSelectCB.addItem("Flock " + handler.getFlocks().size());			
			}
    };
    
    
    //TODO: issue with indexing why trying to remove the first flock when there are two
    /* REMOVE FLOCK - removes currently selected flock*/
    ActionListener BUTTON_REMOVE_FLOCK_ACTION_LISTENER = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(flockSelectCB.getItemCount() == 1) {
				System.out.println("one item onlY");
				handler.clear();
			}else if(flockSelectCB.getItemCount() > 1){
				System.out.println("num of items: " + flockSelectCB.getItemCount());
				System.out.println("cb index: " + flockSelectCB.getSelectedIndex());
				
				handler.removeFlock(flockSelectCB.getSelectedIndex());
				
				System.out.println("cb index: " + flockSelectCB.getSelectedIndex());
				
				flockSelectCB.removeItem(flockSelectCB.getSelectedItem());
				
				
				System.out.println("setting handlers current flock " + flockSelectCB.getSelectedIndex());
				
				handler.setFlockSelected(flockSelectCB.getSelectedIndex());
				
				
			}
			
			/*if(flockSelectCB.getItemCount() > 1) {
				System.out.println(" Selected item " + flockSelectCB.getSelectedIndex());
				handler.removeFlock(flockSelectCB.getSelectedIndex());
				flockSelectCB.remove(flockSelectCB.getSelectedIndex());
				System.out.println(" item removed");
				
				
				
				handler.setFlockSelected(flockSelectCB.getSelectedIndex());
			}*/
			
			
					
		}
    };
    
}

