package au.edu.rmit.cpt222.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import au.edu.rmit.cpt222.interfaces.PlayingCard;

/*
 * The card panel contains:
 * the graphics of the cards,
 * the name of the player,
 * the players total score,
 * and the text card log.
 */
public class CardPanel extends JPanel
{

	private static final long serialVersionUID = -6429467117112866188L;
	private JTextArea cardLog;
	private JPanel cardDisplay;
	private JLabel totalScore;
	private JLabel playerName;
	private int score = 0;
	
	private static final int CARD_WIDTH = 100;
	private static final int CARD_HEIGHT = 145;
	
	public CardPanel()
	{
		super();
		
		this.setLayout(new BorderLayout());		
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		setupPanel();
	}
	
	private void setupPanel()
	{
		this.cardLog = new JTextArea();
		this.cardLog.setEditable(false);
		this.cardLog.setBorder(BorderFactory.createBevelBorder(1));
		this.cardLog.setPreferredSize(new Dimension(200,100));
		this.add(this.cardLog, BorderLayout.EAST);
		
		JPanel cardDisplayContainer = new JPanel();
		cardDisplayContainer.setLayout(new BoxLayout(cardDisplayContainer, BoxLayout.Y_AXIS));
		
		this.playerName = new JLabel("");
		this.playerName.setFont(new Font(playerName.getName(), Font.PLAIN, 30));
		cardDisplayContainer.add(this.playerName, BorderLayout.NORTH);
		
		this.cardDisplay = new JPanel();
		cardDisplay.setLayout(new FlowLayout());
		cardDisplayContainer.add(this.cardDisplay);
		
		this.totalScore = new JLabel("");
		this.totalScore.setFont(new Font(totalScore.getName(), Font.PLAIN, 30));
		cardDisplayContainer.add(this.totalScore, BorderLayout.SOUTH);
		
		this.add(cardDisplayContainer);
	}
	
	//adds a card into the panel
	public void addCard(PlayingCard card)
	{
		//adds the card into the text log
		this.cardLog.setText(this.cardLog.getText() + card.toString() + "\n");
		
		//creates the panel for the card itself
		JPanel cardBox = new JPanel();
		cardBox.setLayout(new BoxLayout(cardBox, BoxLayout.Y_AXIS));
		cardBox.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
		
		//this is the label that will store the image
		JLabel cardLabelValue = new JLabel();
		
		//crate the "URL" for the image based on the card data
		//format is value_of_suit.png
		String cardURL = "images/" + card.getValue() + "_of_" + card.getSuit() + ".png";
		
		//creates the image as an ImageIcon and resizes the image file to fit the card
		ImageIcon cardImage = new ImageIcon(cardURL);
		Image img = cardImage.getImage();
		BufferedImage ret = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics g = ret.createGraphics();
		g.drawImage(img,  0,  0,  CARD_WIDTH, CARD_HEIGHT, null);
		ImageIcon resizedCard = new ImageIcon(ret);
		
		cardLabelValue.setIcon(resizedCard);
		
		cardBox.add(cardLabelValue);
		
		this.cardDisplay.add(cardBox);
	}
	
	//updates the total score text label
	public void updateScore(int score)
	{
		this.score += score;
		String text = Integer.toString(this.score);
		
		this.totalScore.setText(text);
	}
	
	//updates the name field text label
	public void updateName(String name)
	{
		this.playerName.setText(name);
	}
	
	//resets the graphical element of the score to zero
	private void resetScore()
	{
		this.score = 0;
		this.totalScore.setText("0");
	}
	
	//resets the entire card panel to default values
	//for the start of a new hand
	public void resetPanel()
	{
		this.resetScore();
		this.cardLog.setText("");
		this.cardDisplay.removeAll();
		this.cardDisplay.repaint();
	}
}
