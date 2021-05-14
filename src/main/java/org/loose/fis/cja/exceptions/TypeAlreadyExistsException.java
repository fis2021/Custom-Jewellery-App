package org.loose.fis.cja.exceptions;

public class TypeAlreadyExistsException extends Exception {

    private String type;

    public TypeAlreadyExistsException(String type) {
        super(String.format("A product with the type %s already exists!", type));
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
