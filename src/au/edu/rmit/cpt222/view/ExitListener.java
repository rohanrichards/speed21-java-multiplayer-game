package au.edu.rmit.cpt222.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import au.edu.rmit.cpt222.interfaces.GUIGameEngine;

public class ExitListener implements WindowListener {
	
	private GUIGameEngine model;
	private GameWindow view;
	
	public ExitListener(GameWindow view, GUIGameEngine model) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
		model.closingWindow();
		view.dispose();
		System.exit(0);
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
