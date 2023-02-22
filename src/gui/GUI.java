package gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

import boid.BoidF;
import main.Handler;
import main.Window;
import tools.HSBColor;

public class GUI {
	
	private int botPanHeight = 90;
	
	private JFrame frame;
	private JFrame frameFull;
	private int FRAME_WIDTH;
	private int FRAME_HEIGTH;
	
	private Canvas canvas;
	private int canvasWidth, canvasHeight;
	
	private boolean fullscreen = false;
	
	private Handler handler;
	
	/* Bottom panel and its components */
	private BotomPanel botPanel;
	private JSlider alignmentSlider;
	private JSlider cohesionSlider;
	private JSlider separationSlider;
	private JSlider colorSlider;
	private JSlider sizeSlider;
	private JSlider speedSlider;
	private JButton fullScreenBT;
	private JComboBox<String> flockSelectCB; 
	private JCheckBox transferSettingCB;
	
	
	//private Rectangle panelBounds = new Rectangle(50, 50,50,500);
	private Dimension buttonSize = new Dimension(200,30);
	
	private Font font = new Font(null, 0, 10);
	/* Component Colours */
	//Buttons
	private Color btBackgrounClr = new Color(100, 120, 250);
	private Color btForegrounClr = new Color(250, 250, 250);
	//Sliders
	private Color sliderBackgroundClr = new Color(100, 120, 250);
	private Color sliderForegrounClr = new Color(250, 250, 250);
	private static Color sliderTrackClr = new Color(100, 140, 255);
	
	
	public GUI(Handler handler, Window window){
		this.frame = window;
		this.handler = handler;
		
		canvas = newCanvas();
		updateCanvas(frame);

		
		botPanel  = new BotomPanel();
		
		frame.add(canvas, BorderLayout.CENTER);
		frame.add(botPanel, BorderLayout.SOUTH);
		
		frame.revalidate();
	}
	
	/* Set up canvas listeners and settings */
	private Canvas newCanvas() {
		Canvas canvas = new Canvas();
		// Resize the canvas when the frame changes size
		canvas.addHierarchyBoundsListener(new HierarchyBoundsListener() {
			
			@Override
			public void ancestorMoved(HierarchyEvent e) {}

			@Override
			public void ancestorResized(HierarchyEvent e) {
				if(fullscreen) {
					updateCanvas(frameFull);
				}else {
					updateCanvas(frame);
				}
			}
		});
		// ESC - to exit full screen mode
		canvas.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE && fullscreen == true) {
					fullScreenBT.doClick();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
		});
		return canvas;
	}

	/* Recalculate canvas size for a new window */
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

	/* Sets up and initialises bottom panel controls and buttons */
	public class BotomPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		
		public BotomPanel(){
			
			/* Botom Panel setings */
			this.setPreferredSize(new Dimension(0, botPanHeight));
			this.setBackground(new Color(100, 120, 180));
			
			
			JPanel flocksPanel = newJPanel();
			
			 /* Adds a new flock, updates combo box*/
            JButton addFlockBT = newButton("Add Flock", btBackgrounClr, btForegrounClr);
            
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
            transferSettingCB = new JCheckBox("Save Settings");
            transferSettingCB.setBackground(btBackgrounClr);
            transferSettingCB.setForeground(btForegrounClr);
            System.out.println(transferSettingCB.getAlignmentX());
            
            flocksPanel.add(Box.createRigidArea(new Dimension(50,10)));
            flocksPanel.add(addFlockBT);
            flocksPanel.add(Box.createRigidArea(new Dimension(0,10)));
            flocksPanel.add(transferSettingCB);
            add(flocksPanel);
            
			
			JPanel addPanel = newJPanel();
			/* ADD 1 BOID */
            JButton addBoidBT = newButton("Add Boid", btBackgrounClr, btForegrounClr);
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
            JButton add10BoidBT = newButton("Add 10", btBackgrounClr, btForegrounClr);
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
            JButton add100BoidBT = newButton("Add 100", btBackgrounClr, btForegrounClr);
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
            
            JPanel removePanel = newJPanel();
            
            /* Remove currently selected flock */
            JButton removeFlockBT = newButton("Remove Flock",btBackgrounClr, btForegrounClr);
            removeFlockBT.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
	
					flockSelectCB.remove(flockSelectCB.getSelectedIndex());
					handler.removeFlock(flockSelectCB.getSelectedIndex());
					handler.setFlockSelected(flockSelectCB.getSelectedIndex());
					//flockSelectCB.addItem("Flock " + handler.getFlocks().size());			
				}
            });
            
            /* Remove all of the flocks and boids, and reset to initial state */
            JButton clearBT = newButton("Clear",btBackgrounClr, btForegrounClr);
            clearBT.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
	
					flockSelectCB.removeAllItems();
					handler.clear();
					handler.setFlockSelected(flockSelectCB.getSelectedIndex());
					flockSelectCB.addItem("Flock " + handler.getFlocks().size());			
				}
            });
            removePanel.add(removeFlockBT);
            removePanel.add(clearBT);
            add(removePanel);
            
            flockSelectCB = new JComboBox<String>();
            flockSelectCB.addItem("Flock " + handler.getFlocks().size());
            flockSelectCB.setEditable(false);
            flockSelectCB.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(flockSelectCB.getItemCount() != 0) {
						handler.setFlockSelected(flockSelectCB.getSelectedIndex());
						if(!transferSettingCB.isSelected()) {
							resetFlockSliders();
						}
						
					}
					
				}
				private void resetFlockSliders() {
					
					alignmentSlider.setValue(handler.getAlignment());
					cohesionSlider.setValue(handler.getCohesion());
					separationSlider.setValue(handler.getSeparation());
					colorSlider.setValue(handler.getColorValue());
					sizeSlider.setValue(handler.getSize());
					
				}
            });
            add(flockSelectCB);
           
            
            /* Flocking Parameter panel */
            JPanel flockingPanel = newJPanel();
            
            alignmentSlider = jSliderSetup(0, 0 , 100, 0, 10, 5, "Alignment", sliderBackgroundClr, sliderForegrounClr); 
            alignmentSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateAlignment(alignmentSlider.getValue());
				}
            });
            
            cohesionSlider = jSliderSetup(0, 0 , 100, 0, 10, 5, "Cohesion", sliderBackgroundClr, sliderForegrounClr);
            cohesionSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateCohesion(cohesionSlider.getValue());
				}
            });
            
            separationSlider = jSliderSetup(0, 0 , 100, 0, 10, 5, "Separation", sliderBackgroundClr, sliderForegrounClr);
            separationSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateSeparation(separationSlider.getValue());
				}
            });
           
            flockingPanel.add(alignmentSlider);
            flockingPanel.add(cohesionSlider);
            flockingPanel.add(separationSlider);
            add(flockingPanel); 
            
            /* Panel for boid attributes: colour, size, speed*/
            JPanel atributesPanel = newJPanel();
            
            colorSlider = jSliderSetup(0, 0 , 100, 40, 1, 0, "Color", sliderBackgroundClr, sliderForegrounClr);
            colorSlider.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateColor(new HSBColor((float) colorSlider.getValue()/100, 1 , 1));
		
				}
            });
            
            sizeSlider = jSliderSetup(0, 0, 50, 10, 1, 1, "Size", sliderBackgroundClr,sliderForegrounClr);
            sizeSlider.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					
					handler.updateSize(sizeSlider.getValue());
				}
            	
            });
           
            /* Control the speed of the boid */
            speedSlider = jSliderSetup(0, 0, 20, 2, 1, 1, "Speed", sliderBackgroundClr,sliderForegrounClr);
            speedSlider.addChangeListener(new ChangeListener(){

				@Override
				public void stateChanged(ChangeEvent e) {
					handler.updateSpeed(speedSlider.getValue());
					
				}
            	
            });
            atributesPanel.add(colorSlider);
            atributesPanel.add(sizeSlider);
            atributesPanel.add(speedSlider);
            add(atributesPanel);
            
            /* Enables full screen mode:
             * Creates new full sized undecorated frame, transfer contents from windowed frame
             * and dispose of the old frame.
             */
            fullScreenBT = newButton("Fullscreen", btBackgrounClr,  btForegrounClr);
            fullScreenBT.addActionListener(new ActionListener(){
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				synchronized(handler.getFlocks()){
    					
    					if(!fullscreen) {
    						fullscreen = true;
    						
    						//Save the windowed mode frames size
    						FRAME_WIDTH = frame.getWidth();
    						FRAME_HEIGTH = frame.getHeight();
    						
    						//Initialise full screen frame and transfer contents
    						frameFull = new Window(0, 0, "", true);
    						frameFull.setExtendedState(JFrame.MAXIMIZED_BOTH);
    						frameFull.setVisible(true);
    						frameFull.add(frame.getContentPane());
    						
    						//Dispose of old frame
    						frame.dispose();
    						
    						//Hide the control panel
    						botPanel.setVisible(false);
    						//Focus on canvas for immediate control
    						canvas.requestFocus();
    						
    					}else {
    						fullscreen = false;
    						
    						frame = new Window(FRAME_WIDTH, FRAME_HEIGTH,"Flocking Simulator");
    						frame.add(frameFull.getContentPane());
    						frame.setVisible(true);
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
	private JSlider jSliderSetup(int alignment, int minVal, int maxVal, int startVal, int majTick, int minTick, String title, Color backColor, Color frontColor){
		
		
		JSlider slider = new JSlider(alignment, minVal, maxVal, startVal) {
			@Override
	        public void updateUI() {
	            setUI(new CustomSliderUI(this));
	        }
		};
		
		slider.setMajorTickSpacing(majTick);
		slider.setMinorTickSpacing(minTick);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		slider.setPaintLabels(false);
		slider.setPaintTicks(false);
		slider.setBackground(backColor);
		TitledBorder border  = new TitledBorder(title);
		border.setTitleFont(font);
		border.setTitleColor(frontColor);
		slider.setBorder(border);
		
		return slider;
	}
	
	/*Button setup */
	public JButton newButton(String title, Color background, Color foreground) {
		JButton button = new JButton(title);
		button.setFont(font);
		button.setBackground(background);
		button.setForeground(foreground);
		button.setFocusPainted(false);
		
		//button.setAlignmentX(0.5f);
		//button.setAlignmentY(0.5f);

		button.setMaximumSize(new Dimension(100 , 15));
		
		return button;
	}

	/* Set up a JPanel*/
	private JPanel newJPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 1));
		panel.setBorder(new LineBorder(Color.white));
		panel.setBackground(Color.blue);
		//panel.setSize(100,  50);
		panel.setPreferredSize(new Dimension(200, 90));
	;
		
		return panel;
	}
	
	/* Changes the appearance of the default slider: copied from StackOwerflow, user: "weisj" 
	 * 
	 *TODO: figure out how exactly this works and design my own slider
	 *
	 * */
	public static class CustomSliderUI extends BasicSliderUI {

        private static final int TRACK_HEIGHT = 8;
        private static final int TRACK_WIDTH = 8;
        private static final int TRACK_ARC = 5;
        private static final Dimension THUMB_SIZE = new Dimension(10, 10); // Thumb is refered to as the slider ball: this line cganges its size
        private final RoundRectangle2D.Float trackShape = new RoundRectangle2D.Float();

        public CustomSliderUI(final JSlider b) {
            super(b);
        }

        @Override
        protected void calculateTrackRect() {
            super.calculateTrackRect();
            if (isHorizontal()) {
                trackRect.y = trackRect.y + (trackRect.height - TRACK_HEIGHT) / 2;
                trackRect.height = TRACK_HEIGHT;
            } else {
                trackRect.x = trackRect.x + (trackRect.width - TRACK_WIDTH) / 2;
                trackRect.width = TRACK_WIDTH;
            }
            trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, TRACK_ARC, TRACK_ARC);
        }

        @Override
        protected void calculateThumbLocation() {
            super.calculateThumbLocation();
            if (isHorizontal()) {
                thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
            } else {
                thumbRect.x = trackRect.x + (trackRect.width - thumbRect.width) / 2;
            }
        }

        @Override
        protected Dimension getThumbSize() {
            return THUMB_SIZE;
        }

        private boolean isHorizontal() {
            return slider.getOrientation() == JSlider.HORIZONTAL;
        }

        @Override
        public void paint(final Graphics g, final JComponent c) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            super.paint(g, c);
        }

        @Override
        public void paintTrack(final Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            Shape clip = g2.getClip();

            boolean horizontal = isHorizontal();
            boolean inverted = slider.getInverted();

            // Paint shadow.
            g2.setColor(sliderTrackClr);
            g2.fill(trackShape);

            // Paint track background.
            g2.setColor(sliderTrackClr);
            g2.setClip(trackShape);
            trackShape.y += 1;
            g2.fill(trackShape);
            trackShape.y = trackRect.y;

            g2.setClip(clip);

            // Paint selected track.
            if (horizontal) {
                boolean ltr = slider.getComponentOrientation().isLeftToRight();
                if (ltr) inverted = !inverted;
                int thumbPos = thumbRect.x + thumbRect.width / 2;
                if (inverted) {
                    g2.clipRect(0, 0, thumbPos, slider.getHeight());
                } else {
                    g2.clipRect(thumbPos, 0, slider.getWidth() - thumbPos, slider.getHeight());
                }

            } else {
                int thumbPos = thumbRect.y + thumbRect.height / 2;
                if (inverted) {
                    g2.clipRect(0, 0, slider.getHeight(), thumbPos);
                } else {
                    g2.clipRect(0, thumbPos, slider.getWidth(), slider.getHeight() - thumbPos);
                }
            }
            g2.setColor(Color.ORANGE);
            g2.fill(trackShape);
            g2.setClip(clip);
        }

        @Override
        public void paintThumb(final Graphics g) {
            g.setColor(Color.blue);
            g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
        }

        @Override
        public void paintFocus(final Graphics g) {}
    }
	
	public Canvas getCanvas() { return canvas; }
}
