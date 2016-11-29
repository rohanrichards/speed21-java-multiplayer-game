package au.edu.rmit.cpt222.interfaces;

import java.net.Socket;

/*
 * Simply extends the game engine to add required
 * functionality for the single player GUI
 */
public interface GUIGameEngine extends GameEngine {
	
	/*
	 * returns the "main player" that is the reference
	 * to the single user
	 * 
	 */
	Player getMainPlayer();

	/*
	 * sets the main player, usually called after
	 * a new game is started and the user has entered
	 * their name.
	 */
	void setMainPlayer(Player player);

	/*
	 * Simple utility function to handle a bet of zero
	 */
	void resetBet(Player player);
	
	
	void closingWindow();
	
	Socket getSocket();
}