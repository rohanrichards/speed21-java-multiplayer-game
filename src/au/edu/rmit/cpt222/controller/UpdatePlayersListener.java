package au.edu.rmit.cpt222.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import au.edu.rmit.cpt222.interfaces.GUIGameEngine;
import au.edu.rmit.cpt222.interfaces.GameEngine;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.view.GameWindow;

public class UpdatePlayersListener implements ActionListener {

	private GameEngine model;
	private GameWindow view;
	private Thread thread;
	
	public UpdatePlayersListener(GUIGameEngine model, GameWindow view) {
		this.model = model;
		this.view = view;
		
		this.thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Collection<Player> players = model.getAllPlayers();
				view.updatePlayerList(players);
			}
		});
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Thread t = new Thread(this.thread);
		t.start();
		
	}

}
