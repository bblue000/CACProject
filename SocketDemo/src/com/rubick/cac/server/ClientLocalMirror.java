package com.rubick.cac.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;

import com.rubick.cac.client.IOUtils;

/**
 * 
 * @author Yin Yong
 *
 */
class ClientLocalMirror implements Runnable {

	private Socket mClient;
	
	private Collection<ClientConnectListener> mListeners;
	
	private Thread mMyThread;
	
	private long mBuildTimeMillis;
	@Override
	public void run() {
		mMyThread = Thread.currentThread();
		System.out.println("ClientLocalMirror thread = " + mMyThread);
		while (validClientState(mClient)) {
			try {
				InputStream receiver = mClient.getInputStream();
				OutputStream sender = mClient.getOutputStream();
				
				BufferedReader receiverReader = new BufferedReader(new InputStreamReader(receiver));
				PrintWriter senderWriter = new PrintWriter(sender);
				EasyInteractObject easyInteractObject = new EasyInteractObject(this, senderWriter);
				while (validClientState(mClient)) {
					String info = receiverReader.readLine();
					System.out.println("接收到info = " + info);
					CACServerUtils.dispatchClientMessage(mClient,
							CACServerUtils.transferMessage(info),
							easyInteractObject, mListeners);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		IOUtils.close(mClient);
		CACServerUtils.dispatchClientDisconnect(mClient, mListeners);
	}
	
	/*package*/ ClientLocalMirror(Socket client, CachedServerSocket cachedServerSocket) {
		mClient = client;
		mListeners = cachedServerSocket.getClientConnectListeners();
		mBuildTimeMillis = System.currentTimeMillis();
		CACServerUtils.dispatchClientConnect(mClient, mListeners);
		new Thread(this).start();
		System.out.println("创建一个ClientLocalMirror");
	}
	
	/**/ void closeClientConnect() {
		IOUtils.close(mClient);
	}
	
	private static boolean validClientState(Socket client) {
		return !(null == client || !client.isConnected() || !client.isBound() || client.isClosed());
	}
	
	public static void build(Socket client, CachedServerSocket cachedServerSocket) {
		if (!validClientState(client)) {
			System.out.println("unvalid client socket");
			return ;
		}
		new ClientLocalMirror(client, cachedServerSocket);
	}
	
}
