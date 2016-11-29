package au.edu.rmit.cpt222.view;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;


//game info panel is the "status bar"
//in the bottom of the window
public class GameInfoPanel extends JPanel
{

	private static final long serialVersionUID = -8457061836333275055L;
	private String infoText;
	private JLabel infoLabel;

	public GameInfoPanel()
	{
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.infoLabel = new JLabel(" ");
		this.add(this.infoLabel);
	}
	
	//just updates the panel text
	public void updateLabel(String name, int bet, int points)
	{
		this.infoText = name + " | Bet: " + bet + " | Points: " + points;
		this.infoLabel.setText(this.infoText);
	}
}
