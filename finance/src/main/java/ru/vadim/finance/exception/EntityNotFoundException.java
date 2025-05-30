package ru.vadim.finance.exception;

public class EntityNotFoundException extends CustomException {

    public EntityNotFoundException(String nameOfEntity) {
        super(ExceptionType.ENTITY_NOT_FOUND, String.format("Entity - (%s) not found", nameOfEntity));
    }
}
