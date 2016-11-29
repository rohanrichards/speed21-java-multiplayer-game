package au.edu.rmit.cpt222.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import au.edu.rmit.cpt222.interfaces.GUIGameEngine;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.model.SimplePlayer;
import au.edu.rmit.cpt222.view.GameWindow;

public class NewGameActionListener implements ActionListener {
	
	private GUIGameEngine model;
	private GameWindow view;
	public Player player;
	
	public NewGameActionListener(GUIGameEngine guiGameEngine, GameWindow view)
	{
		this.model = guiGameEngine;
		this.view = view;
	}
	
	/*
	 * Creates a new player, requests the user for their name.
	 * Adds the player to the model and enables the play hand button.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//this is a temp player used purely to send the users name to the server
		player = new SimplePlayer("1", view.getUserName(), 0);
		
		//calling this function sends to the server, the server responds with the new player object
		model.addPlayer(player);
		
		view.updateInfoPanelText(player.getPlayerName(), player.getBet(), player.getPoints());
		view.updatePlayerNameLabel(player.getPlayerName());
		view.updateHouseNameLabel("The House");
		view.togglePlaceBetOption(true);
	}

}
