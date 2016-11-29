package au.edu.rmit.cpt222.comms;

import java.io.Serializable;

import au.edu.rmit.cpt222.exceptions.InsufficientFundsException;
import au.edu.rmit.cpt222.interfaces.Command;
import au.edu.rmit.cpt222.interfaces.Connection;
import au.edu.rmit.cpt222.interfaces.GameEngine;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.model.GameEngineServerStub;

/*
 * This command is sent to the server when the player attempts to place a bet
 * the engine checks if the player has enough funds, if not null is returned
 * to the client, which catches this as an error and shows the correct popup to the client
 */
@SuppressWarnings("serial")
public class PlaceBetCommand implements Command, Serializable {

	private Player player;
	private int bet;
	
	public PlaceBetCommand(Player player, int bet) {
		this.player = player;
		this.bet = bet;
	}

	@Override
	public Object execute(GameEngine engine, GameEngineServerStub stub, Connection connection) {
		try 
		{
			engine.placeBet(engine.getPlayer(player.getPlayerId()), bet);
			return true;
		} 
		catch (InsufficientFundsException e) 
		{
			return null;
		}
	}

}
