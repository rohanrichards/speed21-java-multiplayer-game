package au.edu.rmit.cpt222.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import au.edu.rmit.cpt222.comms.GameResultResponse;
import au.edu.rmit.cpt222.comms.NextHouseCardResponse;
import au.edu.rmit.cpt222.comms.NextPlayerCardResponse;
import au.edu.rmit.cpt222.interfaces.GameEngine;
import au.edu.rmit.cpt222.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.interfaces.PlayingCard;

public class ServerStubGameEngineCallback implements GameEngineCallback{

	private int port = 0;
	private Socket socket;
	
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	
	public ServerStubGameEngineCallback(int port) {
		super();
		
		this.port = port;
		
		try 
		{
			System.out.println("server connecting to client server. Port: " + this.port);
			socket = new Socket("localhost", this.port);
			
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			
			System.out.println("Server callback connected to ClientCallbackServer");
			
		} 
		catch (UnknownHostException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void gameResult(Player player, boolean hasWon, GameEngine engine) {
		try {
			outputStream.reset();
			outputStream.writeObject(new GameResultResponse(player, hasWon));
			outputStream.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		try {
			outputStream.writeObject(new NextHouseCardResponse(card));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void nextPlayerCard(Player player, PlayingCard card, GameEngine engine) {
		try 
		{
			outputStream.writeObject(new NextPlayerCardResponse(player, card));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

	@Override
	public void playerBustCard(Player player, PlayingCard card, GameEngine engine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerResult(Player player, int result, GameEngine engine) {
		// TODO Auto-generated method stub
		
	}

}
