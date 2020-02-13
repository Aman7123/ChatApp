package chatApp_Server;

import java.io.*;
import java.net.*;
/**
 * This class represents an instance of each user that connects to the server, a new UserThread will be created for each user
 * @author Aaron
 * @version 1.0
 */
public class UserInstance extends Thread {
	private Socket socket;
	private ChatHelper ChatHelper;
	private PrintWriter pWriter;

	public UserInstance(Socket socket, ChatHelper chatHelper) {
		this.socket = socket;
		this.ChatHelper = chatHelper;
	}

	public void run() {
		try {
			//Get input from clients
			InputStream iStream = socket.getInputStream();
			BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));

			//Build output from ChatHelper to clients
			OutputStream oStream = socket.getOutputStream();
			pWriter = new PrintWriter(oStream, true);

			//The first input from the client is the name they are assigned
			String userName = bReader.readLine();
			ChatHelper.addUserName(userName);

			//Shows a new user the connected people
			listUsers();
			
			//Displays on all clients except new user that they connected
			String serverMessage = "New user connected: " + userName;
			ChatHelper.broadcast(serverMessage, this);

			String clientMessage;
			boolean keepAlive = true;
			
			//This loop checks for user sending a message, and broadcasts that message to other users
			while(keepAlive) {
				clientMessage = bReader.readLine();
				serverMessage = "[" + userName + "]: " + clientMessage;
				ChatHelper.broadcast(serverMessage, this);
				System.out.println(serverMessage);
				if(clientMessage.equals("quit")) {
					ChatHelper.removeUser(userName, this);
					socket.close();

					ChatHelper.broadcast(userName + " has left the chat.", this);
					keepAlive = false;
				}
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}

	/**
	 * This method will pull the hashmap from ChatHelper and list them out, UNLESS no other users are connected in which case it will say so.
	 */
	private void listUsers() {
		if (ChatHelper.hasUsers()) {
			sendMessage("Users chatting: " + ChatHelper.getuserName());
		} else {
			pWriter.println("No other users chatting");
		}
	}

	/**
	 * This method can be called by other methods to send messages to this specific userthread.
	 * @param message The string method for injest.
	 */
	public void sendMessage(String message) {
		pWriter.println(message);
	}
}