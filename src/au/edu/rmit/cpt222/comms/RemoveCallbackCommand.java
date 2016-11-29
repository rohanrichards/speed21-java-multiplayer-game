package au.edu.rmit.cpt222.comms;

import au.edu.rmit.cpt222.interfaces.Command;
import au.edu.rmit.cpt222.interfaces.Connection;
import au.edu.rmit.cpt222.interfaces.GameEngine;
import au.edu.rmit.cpt222.model.GameEngineServerStub;

/*
 * this command removes the GEC for this connection from the server
 * occurs when a player leaves or disconnects
 */
public class RemoveCallbackCommand implements Command {

	public RemoveCallbackCommand() {
		
	}

	@Override
	public Object execute(GameEngine engine, GameEngineServerStub stub, Connection connection) {

		engine.removeGameEngineCallback(connection.getServerCallback());
		
		return null;
	}

}
