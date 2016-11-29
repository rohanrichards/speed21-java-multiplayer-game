package au.edu.rmit.cpt222.comms;

import java.io.Serializable;
import java.util.Collection;

import au.edu.rmit.cpt222.interfaces.Command;
import au.edu.rmit.cpt222.interfaces.Connection;
import au.edu.rmit.cpt222.interfaces.GameEngine;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.model.GameEngineServerStub;

/*
 * this command is sent by a client and is used to start a match
 */
@SuppressWarnings("serial")
public class PlayerDealCommand implements Command, Serializable {

	private Collection<Player> players;

	@Override
	public Object execute(GameEngine engine, GameEngineServerStub stub, Connection connection) {
		players = engine.getAllPlayers();
		
		for(Player player: players)
		{
			System.out.println("dealing player: " + player);
			engine.dealPlayer(player, GameEngineServerStub.DEFAULT_SPEED);
		}
		
		engine.calculateResult();
		
		return true;
	}

}
