package com.kodlamaio.rentalservice.exceptions;

import com.kodlamaio.common.util.exceptions.BusinessException;
import com.kodlamaio.common.util.results.ErrorDataResult;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.security.oauthbearer.secured.ValidateException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestResponseExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity handleBusinessException(BusinessException exception) {
        ErrorDataResult<?> result = new ErrorDataResult<>(exception.getMessage());
        log.error(result.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }


    @ExceptionHandler(TypeMismatchException.class)
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException exception, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        ErrorDataResult<?> result = new ErrorDataResult<>(exception.getMessage());
        log.error(result.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException exception, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        ErrorDataResult<?> result = new ErrorDataResult<>(exception.getMessage());
        log.error(result.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = new ArrayList<>();

        errors.add(String.format("Number of errors : %s", exception.getErrorCount()));

        for (FieldError err : exception.getFieldErrors()) {
            errors.add(err.getField() + " : " + err.getDefaultMessage() + " Rejected Value: "
                    + err.getRejectedValue());
        }

        ErrorDataResult<?> result = new ErrorDataResult<>(errors);

        log.error(result.toString());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(result);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleFeignException(FeignException exception) {

        ErrorDataResult<?> result = new ErrorDataResult<>(exception.getMessage());
        log.error(result.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler
    public ResponseEntity handleRuntimeEception(RuntimeException exception) {
        ErrorDataResult<?> result = new ErrorDataResult<>(exception.getMessage());
        log.error(result.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler
    public ResponseEntity handleValidateException(ValidateException exception) {
        ErrorDataResult<?> result = new ErrorDataResult<>(exception.getMessage());
        log.error(result.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler
    public ResponseEntity handleDataIntegrityViolationExceptionException(DataIntegrityViolationException exception) {
        ErrorDataResult<?> result = new ErrorDataResult<>(exception.getMessage());
        log.error(result.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
