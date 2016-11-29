package au.edu.rmit.cpt222.view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import au.edu.rmit.cpt222.controller.DealHandActionListener;
import au.edu.rmit.cpt222.controller.NewGameActionListener;
import au.edu.rmit.cpt222.controller.PlaceBetActionListener;
import au.edu.rmit.cpt222.controller.QuitGameActionListener;
import au.edu.rmit.cpt222.interfaces.GUIGameEngine;
import au.edu.rmit.cpt222.interfaces.GameEngine;

//this is the "file menu" for the game
public class GameMenuBar extends JMenuBar
{

	private static final long serialVersionUID = -5497886494396522965L;
	private JMenu menu;
	private JMenuItem newGameItem, placeBetItem, dealHandItem, quitItem, updatePlayersItem;
	private GameWindow view;
	private GUIGameEngine model;
	
	public GameMenuBar(GameWindow view, GUIGameEngine model)
	{
		super();
		
		this.view = view;
		this.model = model;
		createMenu(this);
	}
	
	private void createMenu(JMenuBar menuBar)
	{
		menu = new JMenu("Game");
		menu.setMnemonic(KeyEvent.VK_G);
		menu.getAccessibleContext().setAccessibleDescription("This menu contains the game options.");
		
		menuBar.add(menu);
		
		newGameItem = new JMenuItem("New Game");
		newGameItem.setMnemonic(KeyEvent.VK_N);
		newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newGameItem.getAccessibleContext().setAccessibleDescription("Starts a new game with a new player.");
		newGameItem.addActionListener(new NewGameActionListener(this.view.getModel(), this.view));
		menu.add(newGameItem);
		
		placeBetItem = new JMenuItem("Place Bet");
		placeBetItem.setMnemonic(KeyEvent.VK_B);
		placeBetItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		placeBetItem.getAccessibleContext().setAccessibleDescription("Places your bet.");
		placeBetItem.addActionListener(new PlaceBetActionListener(this.view.getModel(), this.view));
		//start disabled, cant place a bet until the player is created.
		placeBetItem.setEnabled(false);
		menu.add(placeBetItem);
		
		dealHandItem = new JMenuItem("Play Hand");
		dealHandItem.setMnemonic(KeyEvent.VK_P);
		dealHandItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		dealHandItem.getAccessibleContext().setAccessibleDescription("Plays a hand.");
		dealHandItem.addActionListener(new DealHandActionListener(this.view.getModel(), this.view));
		//start disabled, cant place a bet until the player is created.
		dealHandItem.setEnabled(false);
		menu.add(dealHandItem);
		
		menu.addSeparator();
		
		quitItem = new JMenuItem("Quit");
		quitItem.setMnemonic(KeyEvent.VK_Q);
		quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		quitItem.getAccessibleContext().setAccessibleDescription("Quits the game.");
		quitItem.addActionListener(new QuitGameActionListener(this.view, this.model));
		menu.add(quitItem);
	}

	//getters and setters
	public JMenuItem getNewGameItem()
	{
		return newGameItem;
	}
	
	public JMenuItem getdealHandItem()
	{
		return dealHandItem;
	}
	
	public JMenuItem getPlaceBetItem()
	{
		return placeBetItem;
	}

	
	public JMenuItem getQuitItem()
	{
		return quitItem;
	}
	
}
