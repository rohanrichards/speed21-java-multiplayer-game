package au.edu.rmit.cpt222.interfaces;

import java.util.*;

import au.edu.rmit.cpt222.exceptions.InsufficientFundsException;

/**
 * Assignment interface (facade) for SADI providing main Speed21 Card Game model
 * functionality
 *
 * @author Caspar Ryan and Mikhail Perepletchikov
 *
 */
public interface GameEngine
{

	public static final int BUST_LEVEL = 21;

	/**
	 * Adds new GameEngineCallback to the GameEngine
	 * 
	 * @param gameEngineCallback
	 *            a client specific implementation of GameEngineCallback used to
	 *            perform display updates etc.
	 *
	 *            you will use a different implementation of the
	 *            GameEngineCallback for GUI and console versions
	 *
	 */
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback);

	/**
	 * Adds a Player to the game
	 * 
	 * @param player
	 *            Player object to add to the game
	 */
	public void addPlayer(Player player);

	/**
	 * This method deals for the house then goes through all players and applies
	 * win or loss to update betting points. GameEngineCallback should be called
	 * for house to allow GUI/Client updates
	 *
	 * @see au.edu.rmit.cpt222.interfaces.GameEngineCallback
	 */
	public void calculateResult();

	/**
	 * Same as dealPlayer() but deals for the house and calls the house versions
	 * of the callback methods on GameEngineCallback, no player parameter is
	 * required
	 *
	 * @param delay
	 *            the delay between cards being dealt in milliseconds
	 * @see GameEngine#dealPlayer(Player, int)
	 */
	public void dealHouse(int delay);

	/**
	 * Deal cards to the player
	 *
	 *
	 * 1. deal a card to the player; 2. call GameEngineCallback.nextCard(...);
	 * 3. continue looping until the player busts (default value of
	 * GameEngine.BUST_LEVEL=21); 4. call
	 * {@link GameEngineCallback#playerResult(Player, int, GameEngine)} with
	 * final result for player (the pre bust total); 5. update the player with
	 * final result so it can be retrieved later.
	 *
	 * @param player
	 *            the current player who will have their result set at the end
	 *            of the hand
	 * @param delay
	 *            the delay between cards being dealt in milliseconds
	 * @see au.edu.rmit.cpt222.interfaces.GameEngineCallback
	 *
	 */
	public void dealPlayer(Player player, int delay);

	/**
	 *
	 * @return an unmodifiable collection of all Players
	 * @see au.edu.rmit.cpt222.interfaces.Player
	 */
	public Collection<Player> getAllPlayers();

	/**
	 * Retrieves a Player indicated by the provided id
	 * 
	 * @param id
	 *            id of the Player to retrieve (should return null if not found)
	 * @return Player object
	 */
	public Player getPlayer(String id);

	/**
	 * A debug method to return a deck of cards containing 52 unique cards in
	 * random/shuffled order
	 *
	 * @return a collection of PlayingCard
	 * @see au.edu.rmit.cpt222.interfaces.PlayingCard
	 */
	public Deque<PlayingCard> getShuffledDeck();

	/**
	 * the implementation should forward the call to the Player class to handle
	 * (this is required for assignment 2, but the initial/base implementation
	 * i.e. checking if the player has sufficient points to place the bet should
	 * already be provided in assignment 1)
	 * 
	 * @param player
	 *            betting Player
	 *
	 * @param bet
	 *            the bet in points
	 * @throws au.edu.rmit.cpt222.exceptions.InsufficientFundsException
	 *             if the player has insufficient points and the bet cannot be
	 *             placed
	 * 
	 */
	public void placeBet(Player player, int bet) throws InsufficientFundsException;

	/**
	 * TODO (do not implement in Ass1)
	 * 
	 * @param gameEngineCallback
	 *            a client specific implementation of GameEngineCallback to be
	 *            removed from the game. NOTE: to be used in Assignment 2. You
	 *            don't need to implement this in Ass1.
	 * 
	 */
	public void removeGameEngineCallback(GameEngineCallback gameEngineCallback);

	/**
	 * Removes a given Player from the game.
	 * 
	 * @param player
	 *            reference to the Player to be removed
	 * @return true if the player existed
	 */
	public boolean removePlayer(Player player);

	/**
	 * 
	 * Sets player's (credit) points to the provided value.
	 * 
	 * @param player
	 *            reference to the Player to be updated with new points
	 * @param totalPoints
	 *            sets player points balance (total points)
	 * 
	 */
	public void setPlayerPoints(Player player, int totalPoints);
}