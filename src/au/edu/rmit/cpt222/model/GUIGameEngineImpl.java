package au.edu.rmit.cpt222.model;

import java.net.Socket;

import au.edu.rmit.cpt222.exceptions.InsufficientFundsException;
import au.edu.rmit.cpt222.interfaces.GUIGameEngine;
import au.edu.rmit.cpt222.interfaces.Player;

public class GUIGameEngineImpl extends GameEngineImpl implements GUIGameEngine {
	
	private Player mainPlayer = null;
	
	public GUIGameEngineImpl() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see au.edu.rmit.cpt222.interfaces.GUIGameEngine#getMainPlayer()
	 */
	@Override
	public Player getMainPlayer()
	{
		return this.mainPlayer;
	}
	
	/* (non-Javadoc)
	 * @see au.edu.rmit.cpt222.model.GUIGameEngine#setMainPlayer(au.edu.rmit.cpt222.model.interfaces.Player)
	 */
	@Override
	public void setMainPlayer(Player player)
	{
		this.mainPlayer = player;
	}
	
	/* (non-Javadoc)
	 * @see au.edu.rmit.cpt222.model.GUIGameEngine#resetBet(au.edu.rmit.cpt222.model.interfaces.Player)
	 */
	@Override
	public void resetBet(Player player)
	{
		player.setPoints(player.getPoints() + player.getBet());
		
		try {
			player.placeBet(0);
		} catch (InsufficientFundsException e) {
			// this never happens because its a bet of zero
			e.printStackTrace();
		}
	}

	@Override
	public void closingWindow() {
		System.exit(0);
	}

	@Override
	public Socket getSocket() {
		// TODO Auto-generated method stub
		return null;
	}
}
