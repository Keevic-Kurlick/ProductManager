package ru.netology.productmanager.repository.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(int id) {
        super("Product with id [" + id + "] not found");
    }
}
