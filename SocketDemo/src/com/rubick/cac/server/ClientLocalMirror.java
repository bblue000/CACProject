package com.rubick.cac.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Collection;

/**
 * 
 * @author Yin Yong
 *
 */
class ClientLocalMirror implements Runnable {

	Socket mClient;
	
	Collection<ClientConnectListener> mListeners;

	protected Thread mMyThread;
	@Override
	public void run() {
		mMyThread = Thread.currentThread();
		System.out.println("ClientLocalMirror thread = " + mMyThread);
		while (mClient.isBound()) {
			
		}
		try {
			InputStream in = mClient.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void build(Socket client, Collection<ClientConnectListener> listeners) {
		mClient = client;
		mListeners = listeners;
		new Thread(this).start();
	}
	
	public static void build(Socket client, CachedServerSocket cachedServerSocket) {
		if (null == client || client.isClosed()) {
			return ;
		}
		new ClientLocalMirror().build(client, cachedServerSocket.mListeners);
	}
	
}
