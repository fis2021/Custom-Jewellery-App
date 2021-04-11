package org.loose.fis.sre.exceptions;

public class UsernameNotExistsException extends AccountException{

    private String username;

    public UsernameNotExistsException(String username) {
        super(String.format("An account with the username %s doesn't exist!", username));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

