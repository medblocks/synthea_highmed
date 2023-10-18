package com.medblocks.common_dependency;

public interface WebSocketSender {
	void sendMessage(String userID, String message);
}
