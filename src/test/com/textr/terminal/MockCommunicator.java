package com.textr.terminal;

/**
 * A mock for {@link Communicator} objects, used for testing. This object holds the last message sent, and allows manual
 * setting of permissions.
 */
public class MockCommunicator implements Communicator {

    /**
     * The last message sent to this Communicator object
     */
    private String lastMessage = null;

    /**
     * The current return value for permission requests, initially true
     */
    private boolean hasPermissions = true;

    /**
     * Saves the given message as the last message sent, and returns the current permissions value.
     * @param msg The message supposedly sent to a client instance
     * @return The current set permissions of this mock communicator
     */
    @Override
    public boolean requestPermissions(String msg) {
        lastMessage = msg;
        return hasPermissions;
    }

    /**
     * Saves the given message as the last message sent.
     * @param msg The message supposedly sent to a client instance
     */
    @Override
    public void sendMessage(String msg) {
        lastMessage = msg;
    }

    /**
     * Returns the last message sent to this communicator instance.
     * @return the last message sent to this communicator instance
     */
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * Sets the permissions value for this communicator instance to the given value.
     * @param hasPermissions The new permissions value to return upon request
     */
    public void setPermissions(boolean hasPermissions) {
        this.hasPermissions = hasPermissions;
    }
}
