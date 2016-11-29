package au.edu.rmit.cpt222.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import au.edu.rmit.cpt222.interfaces.GameEngine;
import au.edu.rmit.cpt222.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.interfaces.PlayingCard;

/*
 * A very standard implementation of the GEC
 * Simply outputs to the logger
 */
public class CLICallbackImpl implements GameEngineCallback
{

	private static Logger logger = Logger.getLogger("assignment1");

	@Override
	public void gameResult(Player player, boolean hasWon, GameEngine engine)
	{
		String message = player.getPlayerName() + " has" + (hasWon ? " won!" : " lost!");
		message += ", Score: " + player.getResult();
		if(hasWon)
			message += ", Points won: " + player.getBet();
		logger.log(Level.INFO, message);
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine)
	{
		String message = "The House Busts! card dealt was: " + card.getValue() + " of " + card.getSuit() + "("
				+ card.getScore() + ")";
		logger.log(Level.INFO, message);
	}

	@Override
	public void houseResult(int result, GameEngine engine)
	{
		String message = "The Houses final score is: " + result + "\n";
		logger.log(Level.INFO, message);
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine)
	{
		String message = "The House was dealt: " + card.getValue() + " of " + card.getSuit() + "(" + card.getScore()
				+ ")";
		logger.log(Level.INFO, message);
	}

	@Override
	public void nextPlayerCard(Player player, PlayingCard card, GameEngine engine)
	{
		String message = player.getPlayerName() + " was dealt: " + card.getValue() + " of " + card.getSuit() + "("
				+ card.getScore() + ")";
		message += ", current score: " + player.getResult();
		logger.log(Level.INFO, message);
	}

	@Override
	public void playerBustCard(Player player, PlayingCard card, GameEngine engine)
	{
		String message = player.getPlayerName() + " Busts! card dealt was: " + card.getValue() + " of " + card.getSuit()
				+ "(" + card.getScore() + ")";
		logger.log(Level.INFO, message);
	}

	@Override
	public void playerResult(Player player, int result, GameEngine engine)
	{
		String message = player.getPlayerName() + " final score is: " + result + "\n";
		logger.log(Level.INFO, message);
	}

}
