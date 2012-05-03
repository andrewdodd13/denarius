package org.ad13.denarius.service.exception;

public class UsernameInUseException extends RuntimeException {
    private static final long serialVersionUID = -730671225135310130L;
    private final String username;

    public UsernameInUseException(String username) {
        super();
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
