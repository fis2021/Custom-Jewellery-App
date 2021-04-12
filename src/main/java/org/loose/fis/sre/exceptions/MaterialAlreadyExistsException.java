package org.loose.fis.sre.exceptions;

public class MaterialAlreadyExistsException extends Exception {

    private String name;

    public MaterialAlreadyExistsException(String name) {
        super(String.format("A material with the name %s already exists!", name));
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
