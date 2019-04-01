package com.saikat.JoePriorityQueue.JoePriorityQueue.Exception;

/**
 * @author Saikat
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String ClientId){
        super(ClientId);
    }
}
