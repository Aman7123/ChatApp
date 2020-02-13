package chatApp_Client;

import java.io.*;
import java.net.*;

public class ReadFromServer extends Thread {
	private BufferedReader bReader;
	private ChatClient client;

	public ReadFromServer(Socket socket, ChatClient client) {
		this.client = client;

		try {
			InputStream iStream = socket.getInputStream();
			bReader = new BufferedReader(new InputStreamReader(iStream));
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	public void run() {
		while (true) {
			try {
				String response = bReader.readLine();
				System.out.println("\n" + response);

				// prints the username after displaying the server's message
				if (client.getUserName() != null) {
					System.out.print("[" + client.getUserName() + "]: ");
				}
			} catch (IOException ex) {
				System.out.println("Error reading from server: " + ex.getMessage());
				ex.printStackTrace();
				break;
			}
		}
	}
}
