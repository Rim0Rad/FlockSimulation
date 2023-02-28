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

///TODO: ADD a check box to allow changing of the settings for all flocks at the same time


public class GUI {
	
	private Window frame; //Window mode frame
	private Window frameFull; //FullScreen mode frame
	private static boolean fullscreen = false;
	
	private int FRAME_WIDTH;
	private int FRAME_HEIGTH;
	
	private CustomCanvas canvas;
	//private int canvasWidth, canvasHeight;
	
	
	
	private Handler handler;
	
	/* Bottom panel and its components */
	private ControlPanel botPanel;
	private Dimension PANEL_BT_SIZE = new Dimension(0, 90);
	//private Dimension BUTTON_SIZE = new Dimension(200,30);
	
	
	public GUI(Handler handler, Window window){
		
		this.frame = window;
		this.handler = handler;
		
		canvas = new CustomCanvas();
		canvas.updateSize(frame.getContentSize(), PANEL_BT_SIZE);
		
		botPanel  = new ControlPanel(PANEL_BT_SIZE, handler);
		
		frame.add(canvas, BorderLayout.CENTER);
		frame.add(botPanel, BorderLayout.SOUTH);
		
		frame.revalidate();
	}
	
	
	
	ActionListener fullScreenActionListener  = new ActionListener(){
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//synchronized(handler.getFlocks()){
				
				if(!GUI.isFullScreen()) {
					fullscreen = true;
					
					//Save window size
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
		//}	
    };
	
	
	public CustomCanvas getCanvas() { return canvas;}
	//public int getCanvasWidth() {return canvasWidth;}
	//public int getCanvasHeigth() {return canvasHeight;}

	public static boolean isFullScreen() {return fullscreen;}
	public static void togleFullScreen() {
		fullscreen = (fullscreen) ? false: true;
	}
	
}
