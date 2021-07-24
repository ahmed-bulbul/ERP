package com.bulbul.erp.error;
import com.bulbul.erp.entity.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
@ResponseStatus
@SuppressWarnings({"unchecked","rawtypes"})
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {



    /**
     * User Not Found Exception Handler
     *
     * */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException exception,
                                                                    WebRequest request) {
        List<String> details = new ArrayList<>();
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,
                exception.getMessage(),details);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);

    }

    /**
     * User Already Exists Exception Handler
     *
     * */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> userAlreadyExists(UserAlreadyExistsException exception,
                                                              WebRequest request) {
        List<String> details = new ArrayList<>();
        ErrorMessage message = new ErrorMessage(HttpStatus.FOUND,
                exception.getMessage(),details);

        return ResponseEntity.status(HttpStatus.FOUND)
                .body(message);

    }

    /**
     * Role Not  Found Exception Handler
     *
     * */
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorMessage> roleNotFoundException(RoleNotFoundException exception,
                                                              WebRequest request) {
        List<String> details = new ArrayList<>();
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,
                exception.getMessage(),details);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);

    }

    /**
     * Invalid Credentials  Exception Handler
     *
     * */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorMessage> invalidCredentialsException(InvalidCredentialsException exception,
                                                              WebRequest request) {
        List<String> details = new ArrayList<>();
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST,
                exception.getMessage(),details);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);

    }

    /**
     * username or password not match exception
     * when changing or resetting password
     * */
    @ExceptionHandler(UsernameOrPasswordNotMatchedException.class)
    public ResponseEntity<ErrorMessage> usernameOrPasswordNotMatchedException(UsernameOrPasswordNotMatchedException exception,
                                                                    WebRequest request) {
        List<String> details = new ArrayList<>();
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE,
                exception.getMessage(),details);

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(message);
    }

    /**
     * Similar password exception
     * Password can not same as current password exception
     * */
    @ExceptionHandler(SimilarPasswordException.class)
    public ResponseEntity<ErrorMessage> similarPasswordException(SimilarPasswordException exception,
                                                                              WebRequest request) {
        List<String> details = new ArrayList<>();
        ErrorMessage message = new ErrorMessage(HttpStatus.FOUND,
                exception.getMessage(),details);

        return ResponseEntity.status(HttpStatus.FOUND)
                .body(message);
    }

    /**
     * This exception handle work on model validation
     * like hibernate validation
     *
     * */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST,
                "Validation Failed",details);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    /**
     * handle internal server error
     * 500 error handle
     * */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());

        ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,
                "Server Error",details);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(message);
    }


}
