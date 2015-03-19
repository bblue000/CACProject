package com.rubick.cac.server;

import java.net.Socket;

/**
 * 客户端连接监听器
 * 
 * @author Yin Yong
 *
 */
public interface ClientConnectListener {
	
	public void onConnect(Socket socket);
	
	public void onReceive(Socket socket, MessageBean message,
			EasyInteractObject easyInteractObject);
	
	public void onDisconnect(Socket socket);
	
}
