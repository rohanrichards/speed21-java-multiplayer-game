package au.edu.rmit.cpt222.interfaces;

/**
 * Assignment interface for SADI representing a PlayingCard
 * 
 * (setting values is handled by the implementing class constructor(s))
 * 
 * @author Caspar Ryan and Mikhail Perepletchikov
 * 
 */
public interface PlayingCard
{

	public enum Suit
	{
		hearts, spades, clubs, diamonds
	};

	public enum Value
	{
		Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
	};

	public static final int DECK_SIZE = 52;

	/**
	 * @return the score value of this card (Ace=1, J, Q, K=10, All others int
	 *         of face value)
	 */
	public int getScore();

	/**
	 * @return the suit of this card based on {@link Suit}
	 */
	public Suit getSuit();

	/**
	 * @return the face value of this card based on {@link Value}
	 */
	public Value getValue();
}