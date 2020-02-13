package chatApp_Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * This method provides help to the running server to ensure smooth execution.
 * @author Aaron
 * @version 1.0
 *
 */
public class ChatHelper {
	private Set<String> userName = new HashSet<>();
	private Set<UserInstance> userThreads = new HashSet<>();
	private String ctM = String.valueOf(System.currentTimeMillis());
	private File logFile = new File("." + "\\" + "ChatApp_logFile" + ctM + ".txt");

	/**
	 * This method is responsible for sending messages to all users MINUS the sending user.
	 */
	public void broadcast(String message, UserInstance excludeUser) {
		for (UserInstance user : userThreads) {
			if (user != excludeUser) {
				user.sendMessage(message);
			}
		}
		logData(message);
	}

	/**
	 * Adds the chosen username of client to Hash.
	 * @param Chosen STRING of the username.
	 */
	public void addUserName(String userToAdd) {
		userName.add(userToAdd);
	}

	/**
	 * When a client sends the quit message this method will ensure they are removed from BOTH hashmaps.
	 * @param userToRemove The string name of the user leaving.
	 * @param userLocation The UserInstance created for each user that must be removed.
	 */
	public void removeUser(String userToRemove, UserInstance userLocation) {
		if (userName.remove(userToRemove)) {
			userThreads.remove(userLocation);
			System.out.println("User: " + userToRemove + " has left the conversation.");
		}
	}

	/**
	 * This method will return a set containing all users in the chat port
	 * @return Set of all users
	 */
	public Set<String> getuserName() {
		return this.userName;
	}
	
	/**
	 * This method will look to see if there is any objects in the username hash, if there isnt any errors this should work. 
	 */
	public boolean hasUsers() {
		boolean hasUsers;
		if(this.userName.isEmpty()) {
			hasUsers = false;
		} else {
			hasUsers = true;
		}
		return hasUsers;
	}

	/**
	 * This method handles the recording of users that connected and places there thread in the hash
	 * @param newUser The userThread of the newly created user.
	 */
	public void UserThreadAdd(UserInstance newUser) {
		this.userThreads.add(newUser);
	}
	
	public void logData(String stringToWrite) {
		try {
			FileWriter fr = new FileWriter(logFile, true);
			fr.write(stringToWrite + "\n");
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
