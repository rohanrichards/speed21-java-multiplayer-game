package au.edu.rmit.cpt222.exceptions;

/**
 * Custom Exception class indicating that the player has insufficient funds
 * 
 * @author Caspar Ryan and Mikhail Perepletchikov
 * 
 */
@SuppressWarnings("serial")
public class InsufficientFundsException extends Exception
{

	/**
	 * default constructor
	 */
	public InsufficientFundsException()
	{
		super("Insufficient Funds Exception");
	}

	/**
	 * @param message
	 *            exception message
	 */
	public InsufficientFundsException(String message)
	{
		super(message);
	}
}
