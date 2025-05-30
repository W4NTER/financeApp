package ru.vadim.finance.exception;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException(Long userId) {
        super(ExceptionType.USER_NOT_FOUND, "Пользователь с ID " + userId + " не найден.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
