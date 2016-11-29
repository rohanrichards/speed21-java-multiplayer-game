package au.edu.rmit.cpt222.interfaces;

import au.edu.rmit.cpt222.exceptions.InsufficientFundsException;

/**
 * Assignment interface for SADI representing the player
 * 
 * @author Caspar Ryan and Mikhail Perepletchikov
 * 
 */
public interface Player
{

	/**
	 * @return the bet as was set with placeBet()
	 */
	public int getBet();

	/**
	 * @return the player ID (set initially by the calling (Client) class)
	 */
	public String getPlayerId();

	/**
	 * @return human readable player name
	 */
	public String getPlayerName();

	/**
	 * 
	 * @return number of points from setPoints()
	 */
	public int getPoints();

	/**
	 * @return the result of the most recent hand (score prior to the last card
	 *         that caused the bust)
	 * 
	 */
	public int getResult();

	/**
	 * 
	 * @return status of the last game played by the player ("true" if won or
	 *         "false" if lost). Initial/default value is "false".
	 */
	public boolean hasWon();

	/**
	 * 
	 * @param bet
	 *            the bet in points
	 * @throws au.edu.rmit.cpt222.exceptions.InsufficientFundsException
	 *             if the player has insufficient points and the bet cannot be
	 *             placed
	 */
	public void placeBet(int bet) throws InsufficientFundsException;

	/**
	 * @param playerName
	 *            human readable player name
	 */
	public void setPlayerName(String playerName);

	/**
	 * @param points
	 *            set total betting points (updated with each win or loss)
	 */
	public void setPoints(int points);

	/**
	 * 
	 * @param result
	 *            the result of the most recent hand (score prior to the last
	 *            card that caused the bust)
	 */
	public void setResult(int result);

	/**
	 * 
	 * @param wonStatus
	 *            status of the last game played by the player ("true" if won or
	 *            "false" if lost)
	 */
	public void setWon(boolean wonStatus);

	/**
	 * 
	 * @return a human readable String that lists the values of this Player
	 *         instance for debugging or console display
	 */
	@Override
	public String toString();
}
