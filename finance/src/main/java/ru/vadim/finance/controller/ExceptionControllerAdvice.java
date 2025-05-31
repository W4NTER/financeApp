package ru.vadim.finance.controller;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.vadim.finance.dto.CustomExceptionDto;
import ru.vadim.finance.exception.EntityAlreadyExistsException;
import ru.vadim.finance.exception.EntityNotFoundException;

import java.util.Arrays;

@ControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomExceptionDto exception(Exception e) {
        LOGGER.info(e.getMessage());
        LOGGER.info(Arrays.toString(e.getStackTrace()));
        return new CustomExceptionDto(e.getClass(), e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomExceptionDto exception(ResourceNotFoundException e) {
        LOGGER.info(e.getMessage());
        LOGGER.info(Arrays.toString(e.getStackTrace()));
        return new CustomExceptionDto(ResourceNotFoundException.class, e.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public CustomExceptionDto entityAlreadyExists(EntityAlreadyExistsException e) {
        LOGGER.info(String.format(e.getMessage()));
        LOGGER.info(Arrays.toString(e.getStackTrace()));
        return new CustomExceptionDto(EntityAlreadyExistsException.class, e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomExceptionDto conflictUser(EntityNotFoundException e) {
        LOGGER.info(String.format(e.getMessage()));
        LOGGER.info(Arrays.toString(e.getStackTrace()));
        return new CustomExceptionDto(EntityNotFoundException.class, e.getMessage());
    }
}
