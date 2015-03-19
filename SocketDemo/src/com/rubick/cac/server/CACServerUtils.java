package com.rubick.cac.server;

import java.net.Socket;
import java.util.Collection;

/**
 * 服务端的处理
 * 
 * @author Yin Yong
 *
 */
class CACServerUtils {
	
	/**
	 * 接收连接延迟时间
	 */
	public static final int SERVER_ACCEPT_TIMEOUT = 30 * 1000;
	
	/**
	 * 客户端连接时，进入闲置阶段的延迟时间
	 */
	public static final int CLIENT_IDLE_TIMEOUT = 30 * 1000;
	
	/**
	 * 关闭与客户端连接的信号
	 */
	public static final String CLOSE_SIGNATURE = "0";

	public static void dispatchClientConnect(Socket client, Collection<ClientConnectListener> c) {
		if (null == c || c.isEmpty()) {
			return;
		}
		for(ClientConnectListener listener : c) {
			listener.onConnect(client);
		}
	}
	
	public static void dispatchClientDisconnect(Socket client, Collection<ClientConnectListener> c) {
		if (null == c || c.isEmpty()) {
			return;
		}
		for(ClientConnectListener listener : c) {
			listener.onDisconnect(client);
		}
	}
	
	public static void dispatchClientMessage(Socket client, MessageBean message,
			EasyInteractObject easyInteractObject,
			Collection<ClientConnectListener> c) {
		if (null == c || c.isEmpty()) {
			return;
		}
		for(ClientConnectListener listener : c) {
			listener.onReceive(client, message, easyInteractObject);
		}
	}
	
	public static MessageBean transferMessage(String message) {
		return new MessageBean(message);
	}
	
}
