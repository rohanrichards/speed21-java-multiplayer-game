package au.edu.rmit.cpt222.view;

import javax.swing.JButton;
import javax.swing.JToolBar;

import au.edu.rmit.cpt222.controller.DealHandActionListener;
import au.edu.rmit.cpt222.controller.NewGameActionListener;
import au.edu.rmit.cpt222.controller.PlaceBetActionListener;
import au.edu.rmit.cpt222.controller.UpdatePlayersListener;


//creates a tool bar with new game and play hand buttons
public class GameToolBar extends JToolBar
{	

	private static final long serialVersionUID = 3260767233284885756L;

	private JButton newGameButton, placeBetButton, playHandButton, updatePlayersButton;
	private GameWindow view;

	public GameToolBar(GameWindow view)
	{
		super();
		
		this.view = view;
		setupToolBar(this);
	}
	
	private void setupToolBar(JToolBar toolBar)
	{
		newGameButton = createButton("Creates a new game.", "New Game");
		newGameButton.addActionListener(new NewGameActionListener(this.view.getModel(), this.view));
		toolBar.add(newGameButton);
		
		placeBetButton = createButton("Places a bet.", "Place Bet");
		placeBetButton.addActionListener(new PlaceBetActionListener(this.view.getModel(), this.view));
		toolBar.add(placeBetButton);
		placeBetButton.setEnabled(false);
		
		playHandButton = createButton("Plays a hand.", "Play Hand");
		playHandButton.addActionListener(new DealHandActionListener(this.view.getModel(), this.view));
		toolBar.add(playHandButton);
		playHandButton.setEnabled(false);
		
		updatePlayersButton = createButton("Gets the list of players.", "Update Players");
		updatePlayersButton.addActionListener(new UpdatePlayersListener(this.view.getModel(), this.view));
		toolBar.add(updatePlayersButton);
		updatePlayersButton.setEnabled(true);
	}
	
	private JButton createButton(String toolTip, String altText)
	{
		JButton button = new JButton(altText);
		button.setToolTipText(toolTip);
		return button;
	}
	
	//getters and setters
	public JButton getNewGameButton()
	{
		return newGameButton;
	}
	
	
	public JButton getPlayHandButton()
	{
		return playHandButton;
	}
	
	public JButton getPlaceBetButton()
	{
		return placeBetButton;
	}
}
