package gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import boid.Boid;
import main.Handler;
import main.Window;

public class GUI {
	
	private JFrame frame;
	private Canvas canvas;
	
	Handler handler;
	
	
	public GUI(Canvas canvas, Handler handler, Window window){
		this.canvas = canvas;
		this.handler = handler;
		
		frame = window;

		frame.add(canvas, BorderLayout.CENTER);
		
		frame.add(new BotomPanel(), BorderLayout.SOUTH);
		
		frame.revalidate();
	}
	
	public class BotomPanel extends JPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public BotomPanel(){
			setLayout(new FlowLayout());
			this.setBackground(new Color(100, 120, 180));
			
            JButton btn = new JButton("Add Boid");
            btn.setBackground(new Color(100, 120, 250));
            btn.setForeground(new Color(250,250,250));
            btn.setFocusPainted(false);
            
            btn.addActionListener(new ActionListener(){
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				synchronized(handler.getBoids()){
    					handler.getBoids().add(new Boid());
    				}
    				System.out.println("Boid added");
    			}
            });
            add(btn);
		}
	}
	
	
}
