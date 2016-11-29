package au.edu.rmit.cpt222.interfaces;

/*
 * a response is similar to a Command
 * it is created on the server side and is sent to the client
 * the "ClientServer" calls the execute method
 */
public interface Response {

	/*
	 * callback is the client side callback
	 * engine is client side engine
	 */
	void execute(GameEngineCallback callback, GUIGameEngine engine);

}