package chatApp_Server;

public class ServerStart {

	private static int portNumber;
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("must run as command: java -jar ChatServer.jar <port-number>");
			System.exit(0);
		} else {
			portNumber = Integer.parseInt(args[0]);
			ChatServer server = new ChatServer(portNumber);
			server.start();
		}
	}

}
