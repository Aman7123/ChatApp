package chatApp_Client;

import java.net.*;
import java.io.*;
/**
 * This method will build the connection from the client chat to the server
 * @author Aaron
 * @version 1.0
 *
 */
public class ChatClient {
	private String ip;
	private int port;
	private String userName;

	public ChatClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void start() {
		try {
			Socket socket = new Socket(ip, port);

			System.out.println("You have connected to chat server.");

			new ReadFromServer(socket, this).start();
			new SendToServer(socket, this).start();

		} catch (Exception ex) {
			System.err.println(ex);
		}

	}
	/**
	 * Sets local variables
	 * @param userName The username for this class to hold so that it can be accessed by the send/recieve methods
	 */
	void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * Gets local variables
	 * @return The username stored private on this class.
	 */
	public String getUserName() {
		return this.userName;
	}

}
