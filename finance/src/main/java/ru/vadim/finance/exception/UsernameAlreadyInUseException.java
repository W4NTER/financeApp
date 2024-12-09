package ru.vadim.finance.exception;


public class UsernameAlreadyInUseException extends CustomException {

    public UsernameAlreadyInUseException(String username) {
        super(ExceptionType.USERNAME_ALREADY_IN_USE,
                String.format("Пользователь с таким username = %s, уже существует", username));
    }
}
