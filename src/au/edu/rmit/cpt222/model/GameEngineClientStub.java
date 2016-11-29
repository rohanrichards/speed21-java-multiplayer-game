package au.edu.rmit.cpt222.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Deque;
import java.util.List;

import au.edu.rmit.cpt222.comms.AddPlayerCommand;
import au.edu.rmit.cpt222.comms.AddServerCommand;
import au.edu.rmit.cpt222.comms.GetAllPlayersCommand;
import au.edu.rmit.cpt222.comms.PlaceBetCommand;
import au.edu.rmit.cpt222.comms.PlayerDealCommand;
import au.edu.rmit.cpt222.comms.RemoveCallbackCommand;
import au.edu.rmit.cpt222.comms.RemovePlayerCommand;
import au.edu.rmit.cpt222.exceptions.InsufficientFundsException;
import au.edu.rmit.cpt222.interfaces.Connection;
import au.edu.rmit.cpt222.interfaces.GUIGameEngine;
import au.edu.rmit.cpt222.interfaces.GameEngine;
import au.edu.rmit.cpt222.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.interfaces.PlayingCard;

public class GameEngineClientStub implements GUIGameEngine{
	
	private GUICallbackImpl callback;
	private Player player;
	private Collection<Player> players;
	private ClientGameEngineCallbackServer callbackServer;
	
	private ObjectOutputStream requestStream;
	private ObjectInputStream responseStream;
	
	private Socket socket;

	public GameEngineClientStub() {
		super();
		
		try 
		{
			System.out.println("connecting to server");
			socket = new Socket("localhost", 10001);
			
			requestStream = new ObjectOutputStream(socket.getOutputStream());			
			responseStream = new ObjectInputStream(socket.getInputStream());
			
			System.out.println("Waiting for ACK");			
			requestStream.writeUTF("CLIENT_ACK");
			requestStream.flush();
			
			System.out.println("ACK Received: " + responseStream.readUTF() + " - Connection established");
		} 
		catch (UnknownHostException e) 
		{
			System.out.println(e.getMessage() + " IP is wrong.");
		} 
		catch(ConnectException e)
		{
			System.out.println("Connection Error: " + e.getMessage() + " - is the server running?");			
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		callback = (GUICallbackImpl) gameEngineCallback;
		
		callbackServer = new ClientGameEngineCallbackServer(callback, this);
		callbackServer.start();
		
		try 
		{
			
			requestStream.writeObject(new AddServerCommand(callbackServer.getPort()));

			Object response = responseStream.readObject();
			
			if(response instanceof GameEngineCallback)
			{				
				
				System.out.println("add server command reported success");

			}


		} 
		catch (IOException | ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addPlayer(Player tempPlayer) {
		
		
		Thread responseThread = new Thread(new Runnable() {
		
			@Override
			public void run() {
				Object response;
				try 
				{
					//first ask the server to remove any old players
					//shouldn't cause any problems if there is no player currently
					requestStream.writeObject(new RemovePlayerCommand(player));
					//just read in the reply, doesn't matter
					response = responseStream.readObject();
					
					requestStream.writeObject(new AddPlayerCommand(tempPlayer.getPlayerName()));
					
					response = responseStream.readObject();
					
					if( response instanceof Player )
					{
						//update gui, bet was success
						player = (Player) response;
						callback.newGameStarted(player);
					}
					else
					{
						System.out.println("add player failure");
					}
				} 
				catch (IOException | ClassNotFoundException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		responseThread.start();
		
	}

	@Override
	public void calculateResult() {
		//this is all handles server side
	}

	@Override
	public void dealHouse(int delay) {
		//this is all handled server side
	}

	@Override
	public void dealPlayer(Player player, int delay) {

			try 
			{
				requestStream.writeObject(new PlayerDealCommand());
				
				responseStream.readObject();
				
			} 
			catch (IOException | ClassNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

	@Override
	public Collection<Player> getAllPlayers() {
		
		Object response;
		try 
		{
			requestStream.writeObject(new GetAllPlayersCommand());
			
			response = responseStream.readObject();

			if( response instanceof List )
			{
				//update gui, bet was success
				players = (Collection<Player>) response;
				return players;
			}
			else
			{
				return null;
			}
		} 
		catch (IOException | ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Player getPlayer(String id) {
		if(player.getPlayerId().equals(id))
		{
			return this.player;			
		}
		else
		{
			return null;
		}
		
	}

	@Override
	public Deque<PlayingCard> getShuffledDeck() {
		// the client never needs a deck, all card functions are server side
		return null;
	}

	@Override
	public void placeBet(Player player, int bet) throws InsufficientFundsException {
		
		try {
			requestStream.writeObject(new PlaceBetCommand(player, bet));
			
			Object response = responseStream.readObject();

			if(response instanceof Boolean)
			{
				if( (boolean) response == true )
				{
					player.placeBet(bet);
				}
				else
				{
					//throw error, insufficient funds, view should catch this and display modal.
					throw new InsufficientFundsException("Player " + player.getPlayerName() + " doesn't have enough points!");
				}
			}

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		// does nothing on the client side
		
	}

	@Override
	public boolean removePlayer(Player player) {
		// this has no function on the client side
		return false;
	}

	@Override
	public void setPlayerPoints(Player player, int totalPoints) {
		player.setPoints(totalPoints);
	}

	@Override
	public Player getMainPlayer() {
		return player;
	}

	@Override
	public void setMainPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void resetBet(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closingWindow() {
		try 
		{
			if (requestStream != null )
			{
				
				this.callbackServer.shutdown();
				requestStream.writeObject(new String("QUIT"));
				requestStream.writeObject(new RemovePlayerCommand(player));
				
			}
			
			
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Socket getSocket()
	{
		return this.socket;
	}

}
