package au.edu.rmit.cpt222.comms;

import java.io.Serializable;

import au.edu.rmit.cpt222.interfaces.GUIGameEngine;
import au.edu.rmit.cpt222.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.interfaces.PlayingCard;
import au.edu.rmit.cpt222.interfaces.Response;

/*
 * this Response is sent to each client each time a new card is drawn for a player (any player)
 * if the card is not for this clients player it is simply logged
 */
@SuppressWarnings("serial")
public class NextPlayerCardResponse implements Serializable, Response{
	
	private Player player;
	private PlayingCard card;
	

	public NextPlayerCardResponse(Player player, PlayingCard card) {
		
		this.player = player;
		this.card = card;
		
	}
	
	/* (non-Javadoc)
	 * @see au.edu.rmit.cpt222.comms.Response#execute(au.edu.rmit.cpt222.interfaces.GameEngineCallback, au.edu.rmit.cpt222.interfaces.GameEngine)
	 */
	@Override
	public void execute(GameEngineCallback callback, GUIGameEngine engine)
	{
		if(engine.getPlayer(player.getPlayerId()) != null)
		{
			callback.nextPlayerCard(player, card, engine);
		}
		else
		{
			System.out.println(player.getPlayerName() + " was dealt: " + card);
		}
		
	}

}
