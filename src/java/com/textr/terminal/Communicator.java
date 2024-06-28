package com.textr.terminal;

public interface Communicator {
    boolean requestPermissions(String msg);
    void sendMessage(String msg);
}
