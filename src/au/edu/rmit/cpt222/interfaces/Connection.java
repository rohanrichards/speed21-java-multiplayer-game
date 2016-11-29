package au.edu.rmit.cpt222.interfaces;

/*
 * An instance of this is created for each client
 * this class handles the incoming Command objects from the client
 * it is also responsible for handling client "QUIT" messages
 * it MUST extend Thread
 */
public interface Connection{

	/*
	 * must always extend thread so must always have a run method
	 */
	void run();

	
	void setServerCallback(GameEngineCallback callback);
	void setPlayer(Player player);

	GameEngineCallback getServerCallback();
	Player getPlayer();

}