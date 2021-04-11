package org.loose.fis.sre.exceptions;

public class IncorrectPasswordException extends AccountException {

    private String password;

    public IncorrectPasswordException(String password) {
        super(String.format("Incorrect password!", password));
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
