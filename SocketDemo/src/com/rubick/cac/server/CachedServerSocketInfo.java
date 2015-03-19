package com.rubick.cac.server;

import java.net.ServerSocket;

public class CachedServerSocketInfo {

	private int mPort;
	CachedServerSocketInfo() {
		
	}
	
	/**
	 * @return 当前缓存的服务器处理Socket的端口号
	 */
	public int getPort() {
		return mPort;
	}
	
	/**
	 * 更新本地
	 */
	void update(ServerSocket server) {
		mPort = null == server ? 0 : server.getLocalPort();
	}
	
}
