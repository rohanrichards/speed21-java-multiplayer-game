package au.edu.rmit.cpt222.comms;

import java.io.Serializable;

import au.edu.rmit.cpt222.interfaces.GUIGameEngine;
import au.edu.rmit.cpt222.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.interfaces.PlayingCard;
import au.edu.rmit.cpt222.interfaces.Response;

/*
 * server sends this to each client when any new card is drawn for the house
 * if the player has no bet, they do not see the card and it is simply logged
 */
@SuppressWarnings("serial")
public class NextHouseCardResponse implements Response, Serializable {

	private PlayingCard card;
	
	public NextHouseCardResponse(PlayingCard card) {
		
		this.card = card;
		
	}

	@Override
	public void execute(GameEngineCallback callback, GUIGameEngine engine) {
		
		if(engine.getMainPlayer().getBet() > 0)
		{
			callback.nextHouseCard(card, engine);
		}
		else
		{
			System.out.println("House was dealt: " + card);
		}
	
	}

}
