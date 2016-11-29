package au.edu.rmit.cpt222.model;

import java.io.Serializable;

import au.edu.rmit.cpt222.exceptions.InsufficientFundsException;
import au.edu.rmit.cpt222.interfaces.Player;

public class SimplePlayer implements Player, Serializable
{

	private static final long serialVersionUID = -6614138412724075531L;
	private String playerId;
	private String playerName;
	private int points;
	private int bet;
	private int score;
	private boolean wonLastMatch;

	// Player constructor
	public SimplePlayer(String playerId, String playerName, int startingPoints)
	{
		assert playerId != " " : "id cannot be empty";
		assert playerName != " " : "name cannot be empty";
		assert startingPoints >= 0 : "starting points must be positive";

		this.playerId = playerId;
		this.playerName = playerName;
		this.points = startingPoints;

		this.bet = 0;
		this.score = 0;
		this.wonLastMatch = false;
	}

	@Override
	public void placeBet(int bet) throws InsufficientFundsException
	{
		assert bet >= 0: "bet cannot be less than zero";

		if(bet <= this.points + this.bet)
		{
			// player has enough points
			// place the bet and deduct points
			if (bet == 0)
			{
				//just check if the bet was zero, treat it as a reset from the model
				this.bet = 0;
			}
			else
			{
				if(this.bet > 0)
				{
					//a bet already exists
					//remove the bet and add it back into points
					this.points += this.bet;
				}
				
				this.points -= bet;
				this.bet = bet;
			}
		}
		else
		{
			// player does not have enough points
			// throw the exception

			throw new InsufficientFundsException("You don't have enough points!");
		}
	}

	// getters and setters

	@Override
	public int getBet()
	{
		return this.bet;
	}

	@Override
	public String getPlayerId()
	{
		return this.playerId;
	}

	@Override
	public String getPlayerName()
	{
		return this.playerName;
	}

	@Override
	public int getPoints()
	{
		return this.points;
	}

	@Override
	public int getResult()
	{
		return this.score;
	}

	@Override
	public boolean hasWon()
	{
		return this.wonLastMatch;
	}

	@Override
	public void setPlayerName(String playerName)
	{
		assert playerName != "";
		assert playerName != " ";

		this.playerName = playerName;
	}

	@Override
	public void setPoints(int points)
	{
		assert points >= 0;

		this.points = points;
	}

	@Override
	public void setResult(int result)
	{
		assert result >= 0;

		this.score = result;
	}

	@Override
	public void setWon(boolean wonStatus)
	{
		this.wonLastMatch = wonStatus;
	}

	@Override
	public String toString()
	{
		return this.getPlayerId() + ": " + this.getPlayerName() + ", bet: " + this.getBet() + ", points: "
				+ this.getPoints() + ", has won: " + this.hasWon();
	}

}
