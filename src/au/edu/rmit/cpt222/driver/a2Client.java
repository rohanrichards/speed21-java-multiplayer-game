package au.edu.rmit.cpt222.driver;

import javax.swing.SwingUtilities;

import au.edu.rmit.cpt222.interfaces.GUIGameEngine;
import au.edu.rmit.cpt222.model.GUICallbackImpl;
import au.edu.rmit.cpt222.model.GameEngineClientStub;
import au.edu.rmit.cpt222.view.GameWindow;

public class a2Client {
	
	public static void main(String[] args) {

		GUIGameEngine model = new GameEngineClientStub();
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				GameWindow view = new GameWindow("client", model);
				model.addGameEngineCallback(new GUICallbackImpl(view));
			}
		});

	}
	
}
