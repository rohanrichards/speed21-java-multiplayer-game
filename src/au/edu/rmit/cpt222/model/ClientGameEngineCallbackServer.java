package au.edu.rmit.cpt222.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import au.edu.rmit.cpt222.interfaces.GUIGameEngine;
import au.edu.rmit.cpt222.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.interfaces.Response;

public class ClientGameEngineCallbackServer extends Thread {

	//this is the client side "listener" server that will receive commands from
	//the server and call the callback of the client
	
	private GameEngineCallback callback;
	private GUIGameEngine engine;
	private ServerSocket serverSocket;
	
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	
	public ClientGameEngineCallbackServer(GameEngineCallback callback, GUIGameEngine engine)
	{
		this.callback = callback;
		this.engine = engine;
		
		try 
		{
			ServerSocket serverSocket = new ServerSocket(0);
			this.serverSocket = serverSocket;
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		try
		{
			System.out.println("Client side server up and running.");

			Socket socket = serverSocket.accept();
			System.out.println("connection from: " + socket.getInetAddress());
			
			inputStream = new ObjectInputStream(socket.getInputStream());
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			
			System.out.println("Client side server streams established.");
			
			while(!serverSocket.isClosed())
			{
				//clientServer is now listening for Response Objects
				
				Object inputStreamObject = inputStream.readObject();
				
				if(inputStreamObject instanceof Response)
				{
					//response objects are just executed without any preamble
					((Response) inputStreamObject).execute(callback, engine);
				}
				
			}
			
		}
		catch(SocketException e)
		{
			System.out.println("Connection Error: " + e.getMessage());
		}
		catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void shutdown()
	{
		try 
		{
			inputStream.close();
			outputStream.close();
			serverSocket.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getPort()
	{
		return this.serverSocket.getLocalPort();
	}
}
