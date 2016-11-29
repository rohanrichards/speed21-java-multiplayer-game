package au.edu.rmit.cpt222.comms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

import au.edu.rmit.cpt222.interfaces.GameEngine;
import au.edu.rmit.cpt222.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.model.GameEngineServerStub;
import au.edu.rmit.cpt222.interfaces.Command;
import au.edu.rmit.cpt222.interfaces.Connection;

public class ConnectionImpl extends Thread implements Connection {
	
	private Socket socket;
	private GameEngine engine;
	private GameEngineServerStub stub;
	private GameEngineCallback serverCallback;
	
	private ObjectInputStream fromClientStream;
	private ObjectOutputStream toClientStream;
	
	private Player player;
	
	public ConnectionImpl(Socket socket, GameEngine engine, GameEngineServerStub stub)
	{
		this.socket = socket;
		this.engine = engine;
		this.stub = stub;
	}

	/* (non-Javadoc)
	 * @see au.edu.rmit.cpt222.comms.Connection#run()
	 */
	@Override
	public void run()
	{
		try 
		{
			System.out.println("New connection thread is running!");
			
			setupStreams();
			listenForCommands();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void setupStreams() throws IOException
	{		
		toClientStream = new ObjectOutputStream(socket.getOutputStream());
		
		fromClientStream = new ObjectInputStream(socket.getInputStream());
		
		System.out.println("Streams connected to client, waiting for ACK.");
		
		System.out.println("ACK Received: " + fromClientStream.readUTF() + " - Sockets set up.");
		
		toClientStream.writeUTF("SERVER_ACK");
		toClientStream.flush();		
	}
	
	private void listenForCommands() throws IOException {
		while( !socket.isClosed() )
		{
			try 
			{
				//thread is listening
				Object fromClientObject = fromClientStream.readObject();
				
				//thread heard something
				if ( fromClientObject instanceof String )
				{
					if( Objects.equals( (String) fromClientObject, new String("QUIT") ) )
					{
						//thread heard quit message
						System.out.println("Received quit command from client.");
						
						//client sends a RemovePlayerCommand immediately after "QUIT" string
						Command command = (Command) fromClientStream.readObject();
						command.execute(engine, stub, this);
						
						engine.removeGameEngineCallback(serverCallback);
						
						System.out.println("Player and GEC removed, closing streams...");
						
						fromClientStream.close();
						toClientStream.close();
						socket.close();
						
						System.out.println("Socket and streams closed, loop exiting");
					}
				}
				else if( fromClientObject instanceof Command )
				{
					//thread heard a command
					System.out.println("Received Command...executing.");
					
					toClientStream.reset();
					
					Command command = (Command) fromClientObject;
					Object commandReturn = command.execute(engine, stub, this);
					
					//commands return null if they fail
					if(commandReturn != null)
					{
						System.out.println("Command success.");
						toClientStream.writeObject(commandReturn);
						toClientStream.flush();
					}
					else
					{
						System.out.println("Command failure.");
						toClientStream.writeObject(null);
						toClientStream.flush();
					}
					
					
				}
				
			} 
			catch(SocketException e)
			{
				/*
				 * this is usually a connection reset
				 * this happens if the client crashes or doesn't close properly
				 */
				System.out.println("Socket Error: " + e.getMessage());
				engine.removeGameEngineCallback(serverCallback);
				
				/*
				 * check if the player was actually added
				 */
				if(this.player != null)
				{
					engine.removePlayer(this.player);					
				}
				
				/*
				 * close down the socket to break the while loop
				 * thread will then die cleanly
				 */
				socket.close();
			}
			catch (ClassNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		}//end of while loop
		
		System.out.println("While loop exited, Connection Thread shutting down.");
	}
	
	/* (non-Javadoc)
	 * @see au.edu.rmit.cpt222.comms.Connection#setServerCallback(au.edu.rmit.cpt222.interfaces.GameEngineCallback)
	 */
	@Override
	public void setServerCallback(GameEngineCallback callback)
	{
		serverCallback = callback;
	}
	
	/* (non-Javadoc)
	 * @see au.edu.rmit.cpt222.comms.Connection#getServerCallback()
	 */
	@Override
	public GameEngineCallback getServerCallback()
	{
		return serverCallback;
	}

	@Override
	public void setPlayer(Player player) {
		this.player = player;
		System.out.println("local player set:" + this.player);
	}

	@Override
	public Player getPlayer() {
		return this.player;
	}
}
