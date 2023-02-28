package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private int DEFAULT_ALIGNMENT = 0;
	private CustomSlider cohesionSlider;
	private int DEFAULT_COHESION = 0;
	private CustomSlider separationSlider;
	private int DEFAULT_SEPARATION = 0;
	private CustomSlider colorSlider;
	private int DEFAULT_COLOR = 50;
	private int MAX_COLOR = 100;
	private int MIN_COLOR = 0;
	private CustomSlider sizeSlider;
	private int DEFAULT_SIZE = 10;
	private int MAX_SIZE = 10;
	private int MIN_SIZE = 0;
	private CustomSlider speedSlider;
	private int DEFAULT_SPEED = 5;
	private int MAX_SPEED = 20;
	private int MIN_SPEED = 0;
	private CustomButton fullScreenBT;
	private CustomComboBox<String> flockSelectCB; 
	private CustomCheckBox transferSettingCB;
	
	/*********************
	 * Component Colours *
	 *********************/
	private Color panelColour = new Color(100, 120, 180);
		//Buttons
	private Color buttonBackgrounClr = new Color(100, 120, 250);
	private Color btForegrounClr = new Color(250, 250, 250);
	
		//Sliders
	private Color sliderBackgroundClr = new Color(100, 120, 250);
	private Color sliderForegrounClr = new Color(250, 250, 250);
	
	
	private Handler handler;
	
	/***************
	 * 			   *
	 * CONSTRUCTOR *
	 *             *
	 ***************/
	
	public ControlPanel(Dimension prefSize, Handler handler){
		
		this.handler = handler;
		
		this.setPreferredSize(prefSize);
		this.setBackground(panelColour);
		
		CPanel flocksPanel = new CPanel();
		
		 /* Adds a new flock, updates combo box*/
		CustomButton addFlockBT = new CustomButton("Add Flock", buttonBackgrounClr, btForegrounClr);
        addFlockBT.addActionListener(BUTTON_ADD_FLOCK_ACTION_LISTENER);
        
        /* When enabled, keeps the current flocks setting for new flock*/
        transferSettingCB = new CustomCheckBox("Save Settings");
        
        //flocksPanel.add(Box.createRigidArea(new Dimension(50,10)));
        flocksPanel.add(addFlockBT);
        //flocksPanel.add(Box.createRigidArea(new Dimension(0,10)));
        flocksPanel.add(transferSettingCB);
        add(flocksPanel);
        
		
		CPanel addPanel = new CPanel();
		
		/* Button to ADD 1 BOID */
		CustomButton addBoidBT = new CustomButton("Add Boid", buttonBackgrounClr, btForegrounClr);
        addBoidBT.addActionListener(BUTTON_ADD_1_BOID_ACTION_LISTENER);
        
        /* Button to ADD 10 BOIDS */
        CustomButton add10BoidBT = new CustomButton("Add 10", buttonBackgrounClr, btForegrounClr);
        add10BoidBT.addActionListener(BUTTON_ADD_10_BOID_ACTION_LISTENER);
        
        /* Button to ADD 100 BOIDS */
        CustomButton add100BoidBT = new CustomButton("Add 100", buttonBackgrounClr, btForegrounClr);
        add100BoidBT.addActionListener(BUTTON_ADD_100_BOID_ACTION_LISTENER);
        
        addPanel.add(addBoidBT);
        addPanel.add(add10BoidBT);
        addPanel.add(add100BoidBT);
        add(addPanel);

        
        CPanel removePanel = new CPanel();
        /* Remove currently selected flock */
        CustomButton removeFlockBT = new CustomButton("Remove Flock", buttonBackgrounClr, btForegrounClr);
        removeFlockBT.addActionListener(BUTTON_REMOVE_FLOCK_ACTION_LISTENER);
        
        /* Remove all of the flocks and boids, and reset to initial state */
        CustomButton clearBT = new CustomButton("Clear", buttonBackgrounClr, btForegrounClr);
        clearBT.addActionListener(BUTTON_CLEAR_ACTION_LISTENER);
        
        removePanel.add(removeFlockBT);
        removePanel.add(clearBT);
        add(removePanel);
        
        /* Combo box for selecting flocks */
        flockSelectCB = new CustomComboBox<String>(handler.getSelectedFlock()+1);  
        flockSelectCB.addActionListener(COMBO_BOX_ITEM_LISTENER);
        //flockSelectCB.addliste
        
        
        add(flockSelectCB);
  
        /* Flocking Parameter panel: alignment, cohesion, separation */
        CPanel flockingPanel = initFlockingParameteSliders();
        add(flockingPanel); 
        
        /* Panel for boid attributes: colour, size, speed*/
        CPanel atributesPanel =  initBoidParameteSliders();
        add(atributesPanel);
        
        /* Enables full screen mode:
         * Creates new full sized undecorated frame, transfer contents from windowed frame
         * and dispose of the old frame.*/
        
        fullScreenBT = new CustomButton("Fullscreen", buttonBackgrounClr,  btForegrounClr);
        //TODO: add action listener from GUI fullScreenBT.addActionListener() to enable fullscreen;
        
        add(fullScreenBT);
	}

	
	/* Initialise boid parameter control slider panel */ 
	private CPanel initBoidParameteSliders() {
		CPanel panel = new CPanel();
		
		colorSlider = new CustomSlider(0, MIN_COLOR, MAX_COLOR, DEFAULT_COLOR, 1, 0, "Color", sliderBackgroundClr, sliderForegrounClr);
        colorSlider.addChangeListener(SLIDER_COLOR_CHANGE_LISTENER);
        
        sizeSlider = new CustomSlider(0, MIN_SIZE, MAX_SIZE, DEFAULT_SIZE, 1, 0, "Size", sliderBackgroundClr,sliderForegrounClr);
        sizeSlider.addChangeListener(SLIDER_SIZE_CHANGE_LISTENER);
        
        /* Control boid's speed */
        speedSlider = new CustomSlider(0, MIN_SPEED, MAX_SPEED, DEFAULT_SPEED, 1, 0, "Speed", sliderBackgroundClr,sliderForegrounClr);
        speedSlider.addChangeListener(SLIDER_SPEED_CHANGE_LISTENER);
       
        panel.add(colorSlider);
        panel.add(sizeSlider);
        panel.add(speedSlider);
		return panel;
	}
	
	/* Initialise flocking parameter control slider panel */
	private CPanel initFlockingParameteSliders() {
		CPanel panel = new CPanel();
		
	 	alignmentSlider = new CustomSlider(0, 0 , 100, 0, 10, 5, "Alignment", sliderBackgroundClr, sliderForegrounClr); 
        alignmentSlider.addChangeListener(SLIDER_ALIGNMENT_CHANGE_LISTENER);
        
        cohesionSlider = new CustomSlider(0, 0 , 100, 0, 10, 5, "Cohesion", sliderBackgroundClr, sliderForegrounClr);
        cohesionSlider.addChangeListener(SLIDER_COHESION_CHANGE_LISTENER);
        
        separationSlider = new CustomSlider(0, 0 , 100, 0, 10, 5, "Separation", sliderBackgroundClr, sliderForegrounClr);
        separationSlider.addChangeListener(SLIDER_SEPARATION_CHANGE_LISTENER);
       
        panel.add(alignmentSlider);
        panel.add(cohesionSlider);
        panel.add(separationSlider);
		
        return panel;
	}
	
	
	
	/* Set slider values to the parameters of the current flock */
	private void setFlockSliders() {
		//TODO clean up
		System.out.println(" curently selected: " + flockSelectCB.getSelectedIndex() + " box contents: " + flockSelectCB.getItemCount() );
		alignmentSlider.setValue(handler.getAlignment(flockSelectCB.getSelectedIndex()));
		cohesionSlider.setValue(handler.getCohesion());
		separationSlider.setValue(handler.getSeparation());
		colorSlider.setValue(handler.getColorValue());
		sizeSlider.setValue(handler.getBoidSize());
		speedSlider.setValue(handler.getBoidSize());
	}
	/* Reset sliders to default values */
	private void resetFlockSliders() {
		
		alignmentSlider.setValue(DEFAULT_ALIGNMENT);
		cohesionSlider.setValue(DEFAULT_COHESION);
		separationSlider.setValue(DEFAULT_SEPARATION);
		
		colorSlider.setValue(DEFAULT_COLOR);
		sizeSlider.setValue(DEFAULT_SIZE);
		speedSlider.setValue(DEFAULT_SPEED);
	}
	
	/* Internal function for button listeners to add specified number of boids to selected flock */
    private void addBoids(int amount) {
    	for(int i = 0; i < amount; i++) {
			BoidF boid = new BoidF();
			boid.updateParameters(alignmentSlider.getValue(), cohesionSlider.getValue(),separationSlider.getValue());
			boid.setColor(colorSlider.getValue());
			boid.setSize(sizeSlider.getValue());
			boid.setSpeed(speedSlider.getValue());
			handler.addBoid(boid);
		}
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
    ActionListener COMBO_BOX_ITEM_LISTENER = new ActionListener() {
    		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("combo action");
			if(flockSelectCB.getItemCount() != 0) {
				handler.setSelectedFlock(flockSelectCB.getSelectedIndex());
				
				if(!transferSettingCB.isSelected()) {
					setFlockSliders();
				}
			}
		}

    };
    
    /* CLEAR BOIDS/FLOCKS - removes all the boid and flocks from the simulation and resets "Flock select" combo box to initial state*/
    ActionListener BUTTON_CLEAR_ACTION_LISTENER = new ActionListener() {
    	
		@Override
		public void actionPerformed(ActionEvent e) {

			flockSelectCB.removeAllItems();
			handler.clear();
			
			flockSelectCB.addItem("Flock " + handler.getFlockCount());		
			handler.setSelectedFlock(flockSelectCB.getSelectedIndex());
			resetFlockSliders();
			
		}
    };
    
    //TODO: issue with indexing why trying to remove the first flock when there are two
    /* 
     * need to decide what I want to happen with flock naming
     * 
     * right now the combo box retains the name when previous flock is removed.
     * But problem is that when the combo box entry is created it uses number of flocks in "flock" list
     * 
     * 
     * */
    
    
    /* REMOVE FLOCK - removes currently selected flock
     * When only one flock is present use clear to reset */
    
   
    ActionListener BUTTON_REMOVE_FLOCK_ACTION_LISTENER = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(flockSelectCB.getItemCount() == 1) {
				handler.clear();
			}else if(flockSelectCB.getItemCount() > 1){
				
				
				int index = flockSelectCB.getSelectedIndex();
				handler.removeFlock(index);
				flockSelectCB.removeItemAt(index);
				
				handler.setSelectedFlock(flockSelectCB.getSelectedIndex());
				setFlockSliders();
				
			}
		}
    };
    
    /* Action listeners for the buttons to add 1, 10 and 100 boids to selected flock*/
    ActionListener BUTTON_ADD_1_BOID_ACTION_LISTENER = new ActionListener(){    	
		@Override
		public void actionPerformed(ActionEvent e) {
			addBoids(1);
		}
    };
    ActionListener BUTTON_ADD_10_BOID_ACTION_LISTENER = new ActionListener(){
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			addBoids(10);
		}
    };
    ActionListener BUTTON_ADD_100_BOID_ACTION_LISTENER = new ActionListener(){
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			addBoids(100);
			
		}
    };
    
    /* Add a new flock to simulation, update combo box with new entry*/

    ActionListener BUTTON_ADD_FLOCK_ACTION_LISTENER = new ActionListener(){
    	
		@Override
		public void actionPerformed(ActionEvent e) {
				
			handler.addFlock();
			flockSelectCB.addItem("Flock " + handler.getFlockCount());
			flockSelectCB.setSelectedIndex(handler.getFlockCount() - 1);
			
			if(transferSettingCB.isSelected()) {
				System.out.println(transferSettingCB.isSelected());
				//setFlockSliders();
			}else {
				resetFlockSliders();
			}

		}
	};
    
}

	



