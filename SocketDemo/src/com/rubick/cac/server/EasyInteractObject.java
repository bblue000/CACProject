package com.rubick.cac.server;

import java.io.PrintWriter;

public class EasyInteractObject {

	private ClientLocalMirror mClientLocalMirror;
	private PrintWriter mSender;
	/*package*/ EasyInteractObject(ClientLocalMirror clientLocalMirror,
			PrintWriter sender) {
		mClientLocalMirror = clientLocalMirror;
		mSender = sender;
	}
	
	public void sendMessage(String message) {
		mSender.println(message);
	}
	
	public void closeClientConnection() {
		mClientLocalMirror.closeClientConnect();
	}
	
}
