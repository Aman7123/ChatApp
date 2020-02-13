package chatApp_Client;

import java.io.*;
import java.net.*;
/**
 * This method MUST BE SEPERATE FROM THE READ THRED, this is because if not the client will not be able to both listen and actively prep the next message to send.
 * 
 * This method controls the data that needs to be transmitted to the server
 * @author Aaron
 * @version 1.0
 *
 */
public class SendToServer extends Thread {
	private PrintWriter pWriter;
	private Socket socket;
	private ChatClient client;

	public SendToServer(Socket ip, ChatClient client) {
		this.socket = ip;
		this.client = client;

		try {
			OutputStream output = ip.getOutputStream();
			pWriter = new PrintWriter(output, true);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	/**
	 * This is method is the starting magic from the chat app, this method allows the user to set there name by it being the first message sent to the server.
	 * The server is expecting the first message to be the user's name so it works out.
	 * 
	 * This method "run" is part of the thread execution and will consistently run waiting to send the next message to the server.
	 */
	public void run() {

		Console console = System.console();
		//Prompt user to set name
		String setUserName = console.readLine("\nEnter your name: ");
		client.setUserName(setUserName);
		pWriter.println(setUserName);

		String message;
		
		while(true) {
			message = console.readLine("[" + client.getUserName() + "]: ");
			pWriter.println(message);
			if (message.equals("bye")) {
				try {
					socket.close();
				} catch (IOException ex) {
					System.out.println("Your message may have not made it to our server. Please try again");
					System.err.println(ex);
				}
			}
		}
	}
}
