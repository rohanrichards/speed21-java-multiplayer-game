package au.edu.rmit.cpt222.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import au.edu.rmit.cpt222.interfaces.GUIGameEngine;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.model.GameEngineServerStub;
import au.edu.rmit.cpt222.view.GameWindow;

public class DealHandActionListener implements ActionListener{

	private GUIGameEngine model;
	private GameWindow view;
	private Thread playCardsThread;
	private Player player;
	
	public DealHandActionListener(GUIGameEngine guiGameEngine, GameWindow view) {
		this.model = guiGameEngine;
		this.view = view;
		
		//this thread handles the main gameplay loop
		//deals the player their cards
		//deals the house
		//tests if the player won
		this.playCardsThread = new Thread(new Runnable()
		{
			public void run()
			{
				view.togglePlayHandOption(false);
				guiGameEngine.dealPlayer(player, GameEngineServerStub.DEFAULT_SPEED);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.player = model.getMainPlayer();
		view.togglePlaceBetOption(false);
		
		Thread t = new Thread(this.playCardsThread);
		t.start();
	}

}
