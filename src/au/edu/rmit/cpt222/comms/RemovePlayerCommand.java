package au.edu.rmit.cpt222.comms;

import java.io.Serializable;

import au.edu.rmit.cpt222.interfaces.Command;
import au.edu.rmit.cpt222.interfaces.Connection;
import au.edu.rmit.cpt222.interfaces.GameEngine;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.model.GameEngineServerStub;

/*
 * this is called to remove the client player object from the server
 * used on disconnects or leaves
 */
@SuppressWarnings("serial")
public class RemovePlayerCommand implements Command, Serializable {

	private Player player;

	public RemovePlayerCommand(Player player) 
	{

		this.player = player;

	}

	@Override
	public Object execute(GameEngine engine, GameEngineServerStub stub, Connection connection) 
	{

		if (this.player != null) 
		{
			engine.removePlayer(engine.getPlayer(player.getPlayerId()));
		}

		return true;

	}

}
