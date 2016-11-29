package au.edu.rmit.cpt222.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import au.edu.rmit.cpt222.exceptions.InsufficientFundsException;
import au.edu.rmit.cpt222.interfaces.GUIGameEngine;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.view.GameWindow;

public class PlaceBetActionListener implements ActionListener {

	private GUIGameEngine model;
	private GameWindow view;
	private Player player;
	
	public PlaceBetActionListener(GUIGameEngine guiGameEngine, GameWindow view)
	{
		this.model = guiGameEngine;
		this.view = view;
	}
	
	/*
	 * Main gameplay loop is executed here
	 * Asks the player for their bet
	 * updates the view and disables the play hand button
	 * deals the hand in a new thread
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.player = model.getMainPlayer();
		
		if(player != null)
		{
			try
			{
				int bet = this.view.getPlayerBet(player.getPlayerName(), player.getPoints());
				
				if(bet == 0)
				{
					model.resetBet(player);
					view.updateInfoPanelText(player.getPlayerName(), player.getBet(), player.getPoints());
					view.togglePlayHandOption(false);
				}
				else if(bet < 0)
				{
					view.showErrorModal("Really?", "Please place an actual real bet.");
				}
				else if(bet > 0)
				{
					model.placeBet(player, bet);
					
					view.resetCardPanels();
					view.revalidate();
					view.updateInfoPanelText(player.getPlayerName(), player.getBet(), player.getPoints());
					view.togglePlayHandOption(true);
				}
			}
			catch(InsufficientFundsException err)
			{
				view.showErrorModal("Oops!", err.getMessage());
			}
		}
	}

}
