package ru.vadim.finance.exception;

@SuppressWarnings({"MutableException"})
public class CustomException extends RuntimeException {
    private Class<? extends CustomException> exceptionType;
    private final String message;

    public CustomException(
            Class<? extends CustomException> exceptionType,
            String message) {
        this.message = message;
        this.exceptionType = exceptionType;
    }

    public CustomException(String message) {
        this.message = message;
    }


    public Class<? extends CustomException> getExceptionType() {
        return exceptionType;
    }

    public String getMessage() {
        return message;
    }


}

