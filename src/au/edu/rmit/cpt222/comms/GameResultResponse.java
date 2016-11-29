package au.edu.rmit.cpt222.comms;

import java.io.Serializable;

import au.edu.rmit.cpt222.interfaces.GUIGameEngine;
import au.edu.rmit.cpt222.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.interfaces.Response;


/*
 * This is sent by the server on the "GameResult" event
 */
@SuppressWarnings("serial")
public class GameResultResponse implements Response, Serializable {

	private Player player;
	private Boolean hasWon;
	
	public GameResultResponse(Player player, Boolean hasWon) {
		
		this.player = player;
		this.hasWon = hasWon;
		
	}

	@Override
	public void execute(GameEngineCallback callback, GUIGameEngine engine) {
		
		//check if this message is for the client
		if(engine.getMainPlayer().getPlayerId().equals(player.getPlayerId()))
		{
			
			//update the clients player object		
			engine.setMainPlayer(player);
			//trigger the gameResult method on the client
			callback.gameResult(player, hasWon, engine);
			
		}
		
		logResult();	
	}
	
	private void logResult()
	{
		System.out.println(player.getPlayerName() + (hasWon ? " won." : " lost"));
	}

}
