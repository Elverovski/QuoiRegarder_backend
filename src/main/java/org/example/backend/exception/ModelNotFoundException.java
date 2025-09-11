package org.example.backend.exception;

public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(Long id, String model) {
        super("Could not find" + model + " with id " + id);
    }
}
