package au.edu.rmit.cpt222.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

import au.edu.rmit.cpt222.interfaces.GUIGameEngine;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.interfaces.PlayingCard;

//this is the main view class, creates a frame and brings together the sub-views
public class GameWindow extends JFrame
{
	
	private static final long serialVersionUID = -2365191751479944756L;
	private GameInfoPanel infoPanel;
	private GameMenuBar menuBar;
	private GameToolBar toolBar;
	private GUIGameEngine model;
	private PlayerListPanel playerNames;
	private CardPanel houseCards, playerCards;
	
	public GameWindow(String title, GUIGameEngine model)
	{
		super(title);
		this.model = model;
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new ExitListener(this, model));
		this.setPreferredSize(new Dimension(800,600));
		
		setupWindow();
	}
	
	private void setupWindow()
	{
		Container pane = this.getContentPane();
		pane.setLayout(new BorderLayout());

		this.infoPanel = new GameInfoPanel();
		pane.add(this.infoPanel, BorderLayout.SOUTH);
		
		this.menuBar = new GameMenuBar(this, this.model);
		this.setJMenuBar(this.menuBar);
		
		this.toolBar = new GameToolBar(this);
		this.add(toolBar, BorderLayout.PAGE_START);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		this.playerCards = new CardPanel();
		this.houseCards = new CardPanel();
		centerPanel.add(houseCards);
		centerPanel.add(playerCards);
		this.add(centerPanel, BorderLayout.CENTER);
		
		this.playerNames = new PlayerListPanel();
		this.add(playerNames, BorderLayout.EAST);
		
		this.pack();
		this.setVisible(true);
	}

	//updates the text in the status bar
	public void updateInfoPanelText(String name, int bet, int points)
	{
		this.infoPanel.updateLabel(name, bet, points);
	}
	
	//updates the player name in the central view "card panel"
	//this is the player area, the bottom pane
	public void updatePlayerNameLabel(String name)
	{
		playerCards.updateName(name);
	}
	
	//updates the house name label, this is the upper pane of the card panel
	public void updateHouseNameLabel(String name)
	{
		houseCards.updateName(name);
	}
	
	public void updatePlayerList(Collection<Player> players)
	{
		playerNames.setPlayers(players);
	}
	
	//asks for the user name and returns user input
	public String getUserName()
	{
		return JOptionPane.showInputDialog(this, "Welcome to Speed21\nPlease enter your name:");
	}
	
	//aks for the users bet, and converts it to an integer and returns the result
	public int getPlayerBet(String name, int points)
	{
		int bet = 0;
		String modalString = name + "\nCurrent Points: " + points + "\nHow much would you like to bet?";
		String modalTitle = "Place a bet";
		
		try
		{
			bet = Integer.parseInt(JOptionPane.showInputDialog(this, modalString, modalTitle, 3));
		}
		catch(NumberFormatException e)
		{
			bet = -1;
			this.showErrorModal("Oops!", "Please enter only numbers");
		}
		
		return bet; 
	}
	
	//just gives an error type modal popup with the provided string
	public void showErrorModal(String title, String message)
	{
		JOptionPane.showMessageDialog(this, message, title, 0);
	}
	
	//gives a status style modal and provides a string based on provided boolean
	private void showResultModal(boolean status)
	{
		String message = status ? "You Won!" : "You Lost!";
		JOptionPane.showMessageDialog(this, message, "Match Ended", 1);
	}
	
	//disables or enables the play hand button
	public void togglePlaceBetOption(boolean setter)
	{
		toolBar.getPlaceBetButton().setEnabled(setter);
		menuBar.getPlaceBetItem().setEnabled(setter);
	}
	
	public void togglePlayHandOption(boolean setter)
	{
		toolBar.getPlayHandButton().setEnabled(setter);
		menuBar.getdealHandItem().setEnabled(setter);
	}
	
	public void toggleNewGameOption(boolean setter)
	{
		toolBar.getNewGameButton().setEnabled(setter);
		menuBar.getNewGameItem().setEnabled(setter);
	}
	
	public void toggleInterfaceLock(boolean setter)
	{
		togglePlaceBetOption(setter);
		togglePlayHandOption(setter);
		toggleNewGameOption(setter);
	}

	// just the public function to handle the result modal
	public void playerResult(Player player, boolean hasWon)
	{
		showResultModal(hasWon);
	}

	//call to update the view with the new card
	public void playerCard(Player player, PlayingCard card)
	{
		playerCards.addCard(card);
		playerCards.updateScore(card.getScore());
	}

	//call to update the house card panel with the new card
	public void houseCard(PlayingCard card)
	{
		houseCards.addCard(card);
		houseCards.updateScore(card.getScore());
	}

	//reset the card panels for a new hand
	public void resetCardPanels()
	{
		houseCards.resetPanel();
		playerCards.resetPanel();
	}
	
	/*
	 **************************
	 * GETTERS AND SETTERS
	 **************************
	 */

	public GUIGameEngine getModel() {
		return model;
	}
	
}
