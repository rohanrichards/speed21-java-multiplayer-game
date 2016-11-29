package au.edu.rmit.cpt222.view;

import java.awt.Dimension;
import java.awt.TextArea;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import au.edu.rmit.cpt222.interfaces.Player;

public class PlayerListPanel extends JPanel{

	private JTextArea textArea = new JTextArea("Player List:");
	
	public PlayerListPanel() {
		
		this.textArea.setEditable(false);
		this.textArea.setBorder(BorderFactory.createBevelBorder(1));
		this.textArea.setPreferredSize(new Dimension(150,470));
		this.add(textArea);
		
	}
	
	public void setPlayers(Collection<Player> players)
	{
		
		if(players != null && !players.isEmpty())
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Player List: \n");
			
			for (Player player : players) {
				sb.append(player.getPlayerName());
				sb.append("\n");
			}
			
			textArea.setText(sb.toString());
		}

	}

}
