package org.mitre.synthea.engine;

public interface SocketLogger {
  void sendLog(String log, String userID);
}
