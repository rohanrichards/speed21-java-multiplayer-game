package au.edu.rmit.cpt222.interfaces;

import au.edu.rmit.cpt222.model.GameEngineServerStub;


/*
 * A command is always sent from the client to the server
 * it is run via the execute method and is always called within the ConnectionImpl Thread (resides on the server)
 */
public interface Command 
{
	/*
	 * engine is the server side game engine
	 * the stub is "the server"
	 * connection is the thread that handles the connection between the server and the client (one per client)
	 */
	Object execute(GameEngine engine, GameEngineServerStub stub, Connection connection);

}