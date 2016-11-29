package au.edu.rmit.cpt222.model;

import java.io.Serializable;

import au.edu.rmit.cpt222.interfaces.PlayingCard;

public class PlayingCardImpl implements PlayingCard, Serializable
{
	/*
	 * added random serialID to get rid of warnings without suppression
	 */
	private static final long serialVersionUID = 7670042893965940579L;
	private Suit suit;
	private Value value;

	public PlayingCardImpl(Suit suit, Value value)
	{
		this.suit = suit;
		this.value = value;
	}

	@Override
	public int getScore()
	{
		// use ordinal here to get the position of the num, needs to be +1 due
		// to zero based ordinance.
		// returns 10 if the ordinance is over 10 (because Value[11-13] are the
		// face cards, jack queen king)
		return this.value.ordinal() + 1 > 10 ? 10 : this.value.ordinal() + 1;
	}

	@Override
	public Suit getSuit()
	{
		return this.suit;
	}

	@Override
	public Value getValue()
	{
		return this.value;
	}
	
	@Override
	public String toString()
	{
		return "Card: " + this.getValue() + " of " + this.getSuit();
	}

}
