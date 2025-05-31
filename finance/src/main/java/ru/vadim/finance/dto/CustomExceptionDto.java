package ru.vadim.finance.dto;

public record CustomExceptionDto(
        Class<? extends Exception> exceptionType,
        String message) {
}