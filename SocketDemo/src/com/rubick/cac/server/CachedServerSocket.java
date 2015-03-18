package com.rubick.cac.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * 保持唯一一个对象
 * 
 * @author Yin Yong
 *
 */
public class CachedServerSocket {

	protected static final int TIMEOUT = 30 * 1000; // 30 sec
	protected ServerSocket mServerSocket;
	protected boolean obtain() {
		if (null == mServerSocket) {
			ServerSocket temp = null;
			while (null == temp) {
				try {
					temp = new ServerSocket(0);
					temp.setSoTimeout(TIMEOUT);
				} catch (IOException e) {
					e.printStackTrace();
					if (null != temp) {
						try {
							temp.close();
						} catch (IOException ignore) { }
					}
				}
			}
			mServerSocket = temp;
			return true;
		}
		return false;
	}
	
	protected final LinkedList<ClientConnectListener> mListeners = new LinkedList<ClientConnectListener>();
	public void registerClientConnectListener(ClientConnectListener listener) {
		synchronized (mListeners) {
			mListeners.add(listener);
		}
	}
	
	public void unregisterClientConnectListener(ClientConnectListener listener) {
		synchronized (mListeners) {
			mListeners.remove(listener);
		}
	}
	
	public CachedServerSocketInfo getCurrentCachedServerInfo() {
		
	}
	
	public void tryAwait() {
		if (obtain()) {
			while (true) {
				try {
					Socket socket = mServerSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
				
			}
		}
	}
	
	protected void postClient(Socket client) {
		ClientLocalMirror.build(client, this);
	}
	
}
