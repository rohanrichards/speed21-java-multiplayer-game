package au.edu.rmit.cpt222.interfaces;

/**
 * Assignment interface for SADI to notify client of GameEngine events e.g.
 * cards being dealt
 * 
 * @author Caspar Ryan and Mikhail Perepletchikov
 * 
 */
public interface GameEngineCallback
{

	/**
	 * called for each Player to indicate whether they won or lost the current
	 * game. use this to update your GUI display or log to console.
	 * 
	 * @param player
	 *            the Player who is playing the game
	 * @param hasWon
	 *            indicates game outcome - won ("true") or lost ("false)
	 * @param engine
	 *            a convenience reference to the engine so the receiver can call
	 *            methods if necessary
	 * @see au.edu.rmit.cpt222.interfaces.GameEngine
	 */
	public void gameResult(Player player, boolean hasWon, GameEngine engine);

	/**
	 * HOUSE version of
	 * {@link GameEngineCallback#playerBustCard(Player, PlayingCard, GameEngine)}
	 * 
	 * @param card
	 *            the bust card that was dealt
	 * @param engine
	 *            a convenience reference to the engine so the receiver can call
	 *            methods if necessary
	 * @see au.edu.rmit.cpt222.interfaces.GameEngine
	 */
	public void houseBustCard(PlayingCard card, GameEngine engine);

	/**
	 * called when the HOUSE has bust with final result (score prior to the last
	 * card that caused the bust)
	 * 
	 * @param result
	 *            the final score of the dealers (house) hand
	 * @param engine
	 *            a convenience reference to the engine so the receiver can call
	 *            methods if necessary
	 * @see au.edu.rmit.cpt222.interfaces.GameEngine
	 */
	public void houseResult(int result, GameEngine engine);

	/**
	 * called as the house is dealing their own hand. use this to update your
	 * GUI display for each card or log to console.
	 * 
	 * @param card
	 *            the next card that was dealt
	 * @param engine
	 *            a convenience reference to the engine so the receiver can call
	 *            methods if necessary
	 * @see au.edu.rmit.cpt222.interfaces.GameEngine
	 */
	public void nextHouseCard(PlayingCard card, GameEngine engine);

	/**
	 * called for each card as the house is dealing to a Player. use this to
	 * update your GUI display for each card or log to console.
	 * 
	 * @param player
	 *            the Player who is receiving cards
	 * @param card
	 *            the next card that was dealt
	 * @param engine
	 *            a convenience reference to the engine so the receiver can call
	 *            methods if necessary
	 * @see au.edu.rmit.cpt222.interfaces.GameEngine
	 */
	public void nextPlayerCard(Player player, PlayingCard card, GameEngine engine);

	/**
	 * called when the card causes the player to bust. this method is called
	 * instead of {@link #nextPlayerCard(Player, PlayingCard, GameEngine)}. this
	 * method is called before {@link #playerResult(Player, int, GameEngine)}.
	 * use this to update your display or log to console.
	 * 
	 * @param player
	 *            the Player who is receiving cards
	 * @param card
	 *            the bust card that was dealt
	 * @param engine
	 *            a convenience reference to the engine so the receiver can call
	 *            methods if necessary
	 * @see au.edu.rmit.cpt222.interfaces.GameEngine
	 */
	public void playerBustCard(Player player, PlayingCard card, GameEngine engine);

	/**
	 * called when the player has bust with final result (score prior to the
	 * last card that caused the bust)
	 * 
	 * @param player
	 *            the current Player
	 * @param result
	 *            the final score of the Player hand
	 * @param engine
	 *            a convenience reference to the engine so the receiver can call
	 *            methods if necessary
	 * @see au.edu.rmit.cpt222.interfaces.GameEngine
	 */
	public void playerResult(Player player, int result, GameEngine engine);
}
