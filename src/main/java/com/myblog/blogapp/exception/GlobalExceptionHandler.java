package com.myblog.blogapp.exception;

import com.myblog.blogapp.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

//wherever exptn occure in our project springboot will knw that
// excptn needs to be given to this class
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{

    //specific Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest) ///it has built in methods to add extra message like model
    {
       ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
               webRequest.getDescription(false) );
       return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleAllException(
            Exception exception,
            WebRequest webRequest) ///it has built in methods to add extra message like model
    {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false) );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
