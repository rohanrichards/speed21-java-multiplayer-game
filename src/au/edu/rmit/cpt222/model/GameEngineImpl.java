package au.edu.rmit.cpt222.model;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

import au.edu.rmit.cpt222.exceptions.InsufficientFundsException;
import au.edu.rmit.cpt222.interfaces.GameEngine;
import au.edu.rmit.cpt222.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.interfaces.Player;
import au.edu.rmit.cpt222.interfaces.PlayingCard;
import au.edu.rmit.cpt222.interfaces.PlayingCard.Suit;
import au.edu.rmit.cpt222.interfaces.PlayingCard.Value;

/*
 * implementation of the Game Engine
 * refer to the interface for generic method comments
 */
public class GameEngineImpl implements GameEngine
{
	private volatile Map<String, Player> players = new LinkedHashMap<String, Player>();
	private volatile List<GameEngineCallback> callbacks = new ArrayList<GameEngineCallback>();
	private ConcurrentLinkedDeque<PlayingCard> deck = this.createShuffledDeck();
	
	private int playerCount = 0;
	
	private Player house = new SimplePlayer("0", "The House", 0);

	public GameEngineImpl()
	{
		super();
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
		this.callbacks.add(gameEngineCallback);
	}

	@Override
	public void addPlayer(Player player)
	{
		System.out.println("Game engine IMPL has added a player (server)");
		players.put(player.getPlayerId(), player);
	}

	@Override
	public void calculateResult()
	{
		//deal the house
		dealHouse(GameEngineServerStub.DEFAULT_SPEED);

		// now have to find which player has the highest score, then compare it
		// to the house
		for(Map.Entry<String, Player> entry : players.entrySet())
		{
			
			Player player = entry.getValue();
			
			if(player.getBet() > 0)
			{
				if(player.getResult() > house.getResult())
				{
					// set the player win state
					player.setWon(true);

					// update their score
					// it is 2x the bet because the best was deducted from points
					// already so they need their bet back plus winnings
					player.setPoints(player.getPoints() + (player.getBet() * 2));
				}
				else if(player.getResult() == house.getResult())
				{
					//scores are equal, bet is returned
					player.setPoints(player.getPoints() + player.getBet());
					
					//we can only have true or false for a result
					//so we just return false, even though it was a draw
					//unfortunately the player gets a "you lose" popup
					//this is a limitation of the interface.
					player.setWon(false);
				}
				else
				{
					//player has lost, the bet was already removed
					//so nothing else needs to happen.
					player.setWon(false);
				}

				// reset the bets for the player to zero (end of game)
				try
				{
					player.placeBet(0);
				}
				catch(InsufficientFundsException e)
				{
					//this will never happen
				}
				// call the result method for each callback
				for(GameEngineCallback callback: callbacks)
				{
					callback.gameResult(player, player.hasWon(), this);
				}
				
			}
		}
	}

	/*
	 * Deals cards for the house until bust 
	 */
	@Override
	public void dealHouse(int delay)
	{
		assert delay > 0;
		assert delay < 5000;
		
		//reset the houses score
		house.setResult(0);
		
		//loop until break
		while(true)
		{
			//grab a card from the deque
			PlayingCard card  = this.getCard();
			int cardScore = card.getScore();
			
			if(isBust(house, card))
			{
				//house busts, call all the callback methods 
				for(GameEngineCallback callback: callbacks)
				{
					callback.houseBustCard(card, this);
					callback.houseResult(house.getResult(), this);
				}
				break;
			}
			else
			{
				//no bust, update result and keep getting cards
				house.setResult(house.getResult() + cardScore);
				for(GameEngineCallback callback: callbacks)
				{
					callback.nextHouseCard(card, this);
				}				
			}
			
			pauseThread(delay);
		}	
	}

	/*
	 * same as the deal house function
	 * however needs to call player specific callbacks 
	 */

	@Override
	public void dealPlayer(Player player, int delay)
	{
		assert delay >= 0 : "delay must be positive!";
		assert delay <= 5000 : "delay must be lower than 5 seconds";

		player.setResult(0);
		
		//players with no bets are skipped
		while(true && player.getBet() > 0)
		{
			PlayingCard card  = this.getCard();
			int cardScore = card.getScore();
			
			if(isBust(player, card))
			{
				for(GameEngineCallback callback: callbacks)
				{
					callback.playerBustCard(player, card, this);
					callback.playerResult(player, player.getResult(), this);
				}	
				break;
			}
			else
			{
				player.setResult(player.getResult() + cardScore);
				
				for(GameEngineCallback callback: callbacks)
				{
					callback.nextPlayerCard(player, card, this);
				}	
			}
			
			pauseThread(delay);
		}
	}
	
	// just pauses the current thread for specified time
	private void pauseThread(int delay)
	{
		assert delay >= 0 : "delay must be positive!";
		assert delay <= 5000 : "delay must be lower than 5 seconds";
		
		try
		{
			Thread.sleep(delay);
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
	
	// simple function that tests if the card will bust the player
	private boolean isBust(Player player, PlayingCard card)
	{
		if(player.getResult() + card.getScore() > BUST_LEVEL)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/*
	 * Helper method to get a card from the deck
	 * If there are no cards left in the deck,
	 * a new deck is created.
	 */
	private PlayingCard getCard()
	{
		if( this.deck.isEmpty() )
		{
			this.deck = createShuffledDeck();
		}
		
		PlayingCard card = this.deck.pop();
		
		return card;
	}
	
	@Override
	public void placeBet(Player player, int bet) throws InsufficientFundsException
	{
		// tries to place a bet, tests if the player has enough points
		// throws the exception if not enough points
		// throw is caught by the handler
		
		System.out.println(player.getPlayerName() + " is placing a bet");
		try
		{
			player.placeBet(bet);
			System.out.println("bet placed " + player.getBet());
		}
		catch(InsufficientFundsException e)
		{
			System.out.println("bet failed throwing error");
			throw new InsufficientFundsException("Player " + player.getPlayerName() + " doesn't have enough points!");
		}

	}

	@Override
	public synchronized void removeGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
		callbacks.remove(gameEngineCallback);
	}

	@Override
	public synchronized boolean removePlayer(Player player)
	{
		
		if(players.remove(player.getPlayerId(), player))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/*
	 * Creates and returns a new shuffled deck of cards
	 */
	private ConcurrentLinkedDeque<PlayingCard> createShuffledDeck()
	{
		// If we want to use Collections.shuffle() we need to create the deck as
		// a List first.
		List<PlayingCard> listDeck = new ArrayList<PlayingCard>();

		// Add in one card of each suit
		for(Suit cardSuit : Suit.values())
		{
			for(Value cardValue : Value.values())
			{
				PlayingCard card = new PlayingCardImpl(cardSuit, cardValue);
				listDeck.add(card);
			}
		}

		// shuffle our list
		Collections.shuffle(listDeck);
		
		return new ConcurrentLinkedDeque<PlayingCard>(listDeck);
	}
	
	/*
	 ******************************************
	 *          GETTERS AND SETTERS
	 ******************************************
	 */
	public Player getHouse() {
		return house;
	}

	public List<GameEngineCallback> getCallbacks() {
		return callbacks;
	}
	
	
	@Override
	public Collection<Player> getAllPlayers()
	{
		// returns the LinkedHashMap as a collection
		return players.values();
	}

	@Override
	public Player getPlayer(String id)
	{
		// will return player at key or null if not found.
		return players.get(id);
	}

	@Override
	public Deque<PlayingCard> getShuffledDeck()
	{
		//creates a new deck each time
		this.deck = createShuffledDeck();
		return this.deck;
	}

	@Override
	public void setPlayerPoints(Player player, int totalPoints)
	{
		assert totalPoints >= 0;
		
		player.setPoints(totalPoints);
	}
	
	public int getNewPlayerId()
	{
		playerCount++;
		return playerCount;
	}

}
