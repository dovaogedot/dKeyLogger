package org.dot;

/**
 * Class that decides whether you are prey or predator
 * 
 * @author dovaogedot
 * @see org.dot.Client
 * @see org.dot.Server
 */

public class Launcher {
	
	/**
	 * Main function
	 * 
	 * @param args who are you?
	 */
	public static void main(String[] args) {
		try{
			String whoami = args[0];
			//String whoami = "server";
			if (whoami.equals("server")) {
				System.out.println("Staring server...");
				new Server();
			} else if (whoami.equals("client")) {
				System.out.println("Starting client...");
				new Client();
			} else {
				System.out.println("<client> or <server>\nThere is no " + whoami);
			}
		} catch (Exception e) {
			System.out.println("I'm so sorry, but you are too stupid to use this tool.");
			new Client();
		}
	}
}
