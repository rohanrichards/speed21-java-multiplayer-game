package au.edu.rmit.cpt222.comms;

import java.io.Serializable;

import au.edu.rmit.cpt222.interfaces.Command;
import au.edu.rmit.cpt222.interfaces.Connection;
import au.edu.rmit.cpt222.interfaces.GameEngine;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.model.GameEngineServerStub;
import au.edu.rmit.cpt222.model.SimplePlayer;

/*
 * creates a new player and ads it to the servers player list
 * returns the player to the client (via the ConnectionImpl) so the GUI can update
 */
@SuppressWarnings("serial")
public class AddPlayerCommand implements Command, Serializable {

	private String playerName;
	
	public AddPlayerCommand(String name)
	{
		this.playerName = name;
	}
	
	/* (non-Javadoc)
	 * @see au.edu.rmit.cpt222.comms.Command#execute(au.edu.rmit.cpt222.interfaces.GUIGameEngine)
	 * 
	 * I used synchronized here because it modifies the player array on the server
	 * I'm not sure it's technically the correct place to do this
	 */
	@Override
	public synchronized Object execute(GameEngine engine, GameEngineServerStub stub, Connection connection)
	{
		Player player = new SimplePlayer(stub.getPlayerCounter(), playerName, GameEngineServerStub.STARTING_POINTS);
		stub.incrementPlayerCounter();
		engine.addPlayer(player);
		connection.setPlayer(player);
		return player;
	}
	
}
