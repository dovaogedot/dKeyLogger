package org.dot;

public class Server {
	
	public Server() {
		try {
			FTPManager.connect();
			if (FTPManager.download())
				System.out.println("Completed.");
			else
				System.out.println("Failed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
