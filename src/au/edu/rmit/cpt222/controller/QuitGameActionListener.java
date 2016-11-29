package au.edu.rmit.cpt222.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import au.edu.rmit.cpt222.interfaces.GUIGameEngine;
import au.edu.rmit.cpt222.view.GameWindow;

public class QuitGameActionListener implements ActionListener {

	private GameWindow view;
	private GUIGameEngine model;
	
	public QuitGameActionListener(GameWindow view, GUIGameEngine model)
	{
		this.view = view;
		this.model = model;
	}
	
	/**
	 * Simply closes the application
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
		
	}

}
