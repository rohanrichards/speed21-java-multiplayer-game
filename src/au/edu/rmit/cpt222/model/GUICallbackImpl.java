package au.edu.rmit.cpt222.model;

import au.edu.rmit.cpt222.interfaces.GameEngine;
import au.edu.rmit.cpt222.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.interfaces.PlayingCard;
import au.edu.rmit.cpt222.view.GameWindow;

/*
 * A GUI implementation of the GEC
 * extends the CLI to add some functionality
 * does not call the CLI methods, do not expect CLI output.
 * if you want CLI output, create a new CLICallbackImpl and add it to the model
 */
public class GUICallbackImpl extends CLICallbackImpl implements GameEngineCallback
{
	private GameWindow view;

	public GUICallbackImpl(GameWindow view)
	{
		super();
		this.view = view;
	}
	
	//to be called when a new game is created
	public void newGameStarted(Player player)
	{
		view.updateInfoPanelText(player.getPlayerName(), player.getBet(), player.getPoints());
		view.updatePlayerNameLabel(player.getPlayerName());
		view.updateHouseNameLabel("The House");
		view.togglePlaceBetOption(true);
	}
	
	//called after a bet is placed
	public void betPlaced(Player player, int bet)
	{
		view.resetCardPanels();
		view.revalidate();
		view.updateInfoPanelText(player.getPlayerName(), player.getBet(), player.getPoints());
		view.togglePlaceBetOption(false);
	}
	
	//alerts the player of the result of the game
	@Override
	public void gameResult(Player player, boolean hasWon, GameEngine engine)
	{
		view.playerResult(player, hasWon);
		view.updateInfoPanelText(player.getPlayerName(), player.getBet(), player.getPoints());
		view.togglePlaceBetOption(true);
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine)
	{
		//I don't do anything with the GUI for this event
	}

	@Override
	public void houseResult(int result, GameEngine engine)
	{
		//I don't do anything with the GUI for this event
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine)
	{
		view.houseCard(card);
	}

	@Override
	public void nextPlayerCard(Player player, PlayingCard card, GameEngine engine)
	{
		view.playerCard(player, card);
	}

	@Override
	public void playerBustCard(Player player, PlayingCard card, GameEngine engine)
	{
		//I don't do anything with the GUI for this event
	}

	@Override
	public void playerResult(Player player, int result, GameEngine engine)
	{
		//I don't do anything with the GUI for this event
	}

}
