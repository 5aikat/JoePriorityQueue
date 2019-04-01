package com.saikat.JoePriorityQueue.JoePriorityQueue;

import com.saikat.JoePriorityQueue.JoePriorityQueue.Exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Saikat
 */

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<RestResponse> handleAllException(Exception ex, WebRequest request){
     RestResponse restResponse = new RestResponse(request.getContextPath());
     restResponse.setMessage(ex.getMessage());
     return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public final ResponseEntity<RestResponse> handleOrderNotFoundException(Exception ex, WebRequest request){
        RestResponse restResponse = new RestResponse(request.getContextPath());
        restResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(restResponse,HttpStatus.NOT_FOUND);
    }

    public final ResponseEntity<RestResponse> handleEmptyQueueException(Exception ex, WebRequest request){
        RestResponse restResponse = new RestResponse(request.getContextPath());
        restResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(restResponse,HttpStatus.NOT_FOUND);
    }

}
