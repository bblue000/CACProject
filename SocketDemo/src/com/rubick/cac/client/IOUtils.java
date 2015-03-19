package com.rubick.cac.client;

import java.io.Closeable;
import java.net.ServerSocket;
import java.net.Socket;

public class IOUtils {

	private IOUtils(){}
	
	public static void close(Closeable closeable) {
		try {
			closeable.close();
		} catch (Exception e) { }
	}
	
	public static void close(Socket closeable) {
		try {
			closeable.close();
		} catch (Exception e) { }
	}
	
	public static void close(ServerSocket closeable) {
		try {
			closeable.close();
		} catch (Exception e) { }
	}
	
}
