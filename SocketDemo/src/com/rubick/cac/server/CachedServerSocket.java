package com.rubick.cac.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 保持唯一一个对象
 * 
 * @author Yin Yong
 *
 */
public class CachedServerSocket {

	private static final CachedServerSocket sInstance = new CachedServerSocket();
	public static CachedServerSocket getInstance() {
		return sInstance;
	}
	
	private CachedServerSocketInfo mCachedServerSocketInfo = new CachedServerSocketInfo();
	private ServerSocket mServerSocket;
	protected boolean obtain() {
		if (null == mServerSocket) {
			ServerSocket temp = null;
			while (null == temp) {
				try {
					temp = new ServerSocket(0);
					temp.setSoTimeout(CACServerUtils.SERVER_ACCEPT_TIMEOUT);
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
			mCachedServerSocketInfo.update(mServerSocket);
			System.out.println("新ServerSocket创建成功，port = " + mCachedServerSocketInfo.getPort());
			return true;
		}
		return false;
	}
	
	/*package*/ ServerSocket getCachedServerSocket() {
		return mServerSocket;
	}
	
	private final LinkedList<ClientConnectListener> mListeners = new LinkedList<ClientConnectListener>();
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
	
	/*package*/ List<ClientConnectListener> getClientConnectListeners() {
//		synchronized (mListeners) {
//			List<ClientConnectListener> copy = new ArrayList<ClientConnectListener>(mListeners.size());
//			copy.addAll(mListeners);
//			return copy;
//		}
		synchronized (mListeners) {
			return Collections.unmodifiableList(mListeners);
		}
	}
	
	public CachedServerSocketInfo getCurrentCachedServerInfo() {
		return mCachedServerSocketInfo;
	}
	
	public void startAccept() {
		if (obtain()) {
			postServer(mServerSocket);
		}
	}
	
	/*package*/ void postServer(final ServerSocket server) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Socket socket = mServerSocket.accept();
						postClient(socket);
					} catch (SocketTimeoutException e) {
						e.printStackTrace();
						break;
					} catch (IOException e) {
						e.printStackTrace();
						break;
					}
				}
			}
		}).start();
	}
	
	/**
	 * 对单个客户端单独处理各自的链接
	 */
	/*package*/ void postClient(Socket client) {
		ClientLocalMirror.build(client, this);
	}
}
