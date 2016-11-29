package au.edu.rmit.cpt222.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import au.edu.rmit.cpt222.comms.ConnectionImpl;
import au.edu.rmit.cpt222.interfaces.GameEngine;

public class GameEngineServerStub {
	
	private GameEngine engine = new GameEngineImpl();
	private int playerCounter = 0;
	private List<ConnectionImpl> clientConnections = new ArrayList<ConnectionImpl>();
	
	public static final int STARTING_POINTS = 100;
	public static final int DEFAULT_SPEED = 500;

	public GameEngineServerStub(final int port) 
	{		
		
		listenForConnections(port);
		
	}
	
	public void incrementPlayerCounter()
	{
		playerCounter++;
	}
	
	public String getPlayerCounter()
	{
		return Integer.toString(this.playerCounter);
	}
	
	//the server stub listens for connections and creates new Connection objects for each one
	private void listenForConnections(final int port)
	{
		try( ServerSocket serverSocket = new ServerSocket(port) )
		{
			System.out.println("Server up and running");
			while( !serverSocket.isClosed() )
			{
				
				Socket socket = serverSocket.accept();
				
				System.out.println("connection from: " + socket.getInetAddress() + ":" + socket.getPort());
				
				ConnectionImpl clientConnection = new ConnectionImpl(socket, engine, this);
				clientConnection.start();
				clientConnections.add(clientConnection);

			}
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
