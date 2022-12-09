package com.kodlamaio.rentalservice.exceptions;

import com.kodlamaio.common.util.exceptions.BusinessException;
import com.kodlamaio.rentalservice.utilities.results.ApiErrorResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
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
    public ResponseEntity<?> handleNotFoundExceptions(BusinessException ex,
                                                      WebRequest request) {

        var response = new ApiErrorResponse<>();
        response.setHttpStatus(HttpStatus.NOT_FOUND);
        response.setStatusCode(HttpStatus.NOT_FOUND.value());

        // response.setTimestamp(ResponseMessage.timestamp);
        response.setPath(request.getDescription(false));
        response.setErrors(Arrays.asList(ex.getMessage()));

        log.error(response.toString());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }


    @ExceptionHandler(TypeMismatchException.class)
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        // return super.handleTypeMismatch(ex, headers, status, request);

        var response = new ApiErrorResponse<>();
        response.setPath(request.getDescription(false));
        response.setErrors(Arrays.asList(ex.getMessage(), "Required Type: " + ex.getRequiredType()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        // return super.handleMissingPathVariable(ex, headers, status, request);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("MissingPathVariable");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = new ArrayList<>();

        errors.add(String.format("Number of errors : %s", ex.getErrorCount()));

        for (FieldError err : ex.getFieldErrors()) {
            errors.add(err.getField() + " : " + err.getDefaultMessage() + " Rejected Value: "
                    + err.getRejectedValue());
        }

        var response = new ApiErrorResponse<>();
        response.setPath(request.getDescription(false));
        response.setMessage("MethodArgumentNotValid");
        response.setErrors(errors);

        log.error(response.toString());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleFeignException(FeignException exception) {

        ApiErrorResponse response = new ApiErrorResponse<>();
        response.setMessage(exception.getMessage());
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler
    public ResponseEntity handleRuntimeEception(RuntimeException exception) {
        ApiErrorResponse response = new ApiErrorResponse();
        response.setMessage(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
