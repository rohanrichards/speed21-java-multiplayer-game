package au.edu.rmit.cpt222.comms;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import au.edu.rmit.cpt222.interfaces.Command;
import au.edu.rmit.cpt222.interfaces.Connection;
import au.edu.rmit.cpt222.interfaces.GameEngine;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.model.GameEngineServerStub;

/*
 * Simply gets the list of players from the server
 */
@SuppressWarnings("serial")
public class GetAllPlayersCommand implements Command, Serializable {

	@Override
	public Object execute(GameEngine engine, GameEngineServerStub stub, Connection connection) {
		List<Player> players = new LinkedList<Player>(engine.getAllPlayers());
		
		return players;
	}

}
