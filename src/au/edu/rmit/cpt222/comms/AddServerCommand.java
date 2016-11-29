package au.edu.rmit.cpt222.comms;

import java.io.Serializable;

import au.edu.rmit.cpt222.interfaces.Command;
import au.edu.rmit.cpt222.interfaces.Connection;
import au.edu.rmit.cpt222.interfaces.GameEngine;
import au.edu.rmit.cpt222.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.model.GameEngineServerStub;
import au.edu.rmit.cpt222.model.ServerStubGameEngineCallback;

/*
 * this tells the server to set up the Response connection between the client
 * it creates the callback that resides on the server, which is used to send 
 * Response objects back to the "client server"
 */
@SuppressWarnings("serial")
public class AddServerCommand implements Command, Serializable {

	private int port;
	
	public AddServerCommand(int port)
	{
		this.port = port;
	}

	@Override
	public Object execute(GameEngine engine, GameEngineServerStub stub, Connection connection) 
	{
		GameEngineCallback serverCallback = new ServerStubGameEngineCallback(this.port);
		engine.addGameEngineCallback(serverCallback);
		connection.setServerCallback(serverCallback);
		return true;
	}

}
