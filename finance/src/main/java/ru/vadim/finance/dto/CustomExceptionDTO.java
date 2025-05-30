package ru.vadim.finance.dto;


import ru.vadim.finance.exception.ExceptionType;

public record CustomExceptionDTO(
        ExceptionType exceptionType,
        String message) {

    public CustomExceptionDTO(String message) {
        this(ExceptionType.ANY_EXCEPTION, message);
    }
}
