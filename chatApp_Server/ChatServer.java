package chatApp_Server;

import java.io.*;
import java.net.*;
/**
 * 
 * @author Aaron
 * @version 1.0
 * Made for CIS 390 Exam 1
 *
 */
public class ChatServer {
	private int portNumber;
	ChatHelper ChatHelper = new ChatHelper();
	/**
	 * Init. the chat server, a port must be passed in by main.
	 * @param portNumber The port to bind to on the network.
	 */
	public ChatServer(int portNumber) {
		this.portNumber = portNumber;
	}

	/**
	 * This method will start the process of running the server
	 */
	public void start() {
		try {
			ServerSocket sSocket = new ServerSocket(portNumber);
			System.out.println("The chat server will now start on: " + portNumber);
			System.out.println("This server logs all chat conversations, look in the running folder for file called: ChatApp_logFile");

			while (true) {
				Socket bindingPort = sSocket.accept();
				System.out.println("New user is connecting...");
				
				UserInstance newUser = new UserInstance(bindingPort, ChatHelper);
				ChatHelper.UserThreadAdd(newUser);
				newUser.start();
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
}