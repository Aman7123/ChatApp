package chatApp_Client;

/**
 * Main
 * @author Aaron
 *
 */
public class StartClient {

	public static void main(String[] args) {
		String hostname;
		int portNumber;
		
		if (args.length < 2) {
			System.out.println("must run as command: java -jar ChatClient.jar <server-ip> <port-number>");
			System.exit(0);
		} else {
			hostname = args[0];
			portNumber = Integer.parseInt(args[1]);
	
			ChatClient client = new ChatClient(hostname, portNumber);
			client.start();			
		}
	}
}
