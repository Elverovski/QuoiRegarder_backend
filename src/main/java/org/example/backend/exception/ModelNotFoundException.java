package org.example.backend.exception;

public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(Long id, String model) {
        super("Could not find any " + model + " with id " + id);
    }
}
