package com.saikat.JoePriorityQueue.JoePriorityQueue;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.saikat.JoePriorityQueue.JoePriorityQueue.Exception.EmptyQueueException;
import com.saikat.JoePriorityQueue.JoePriorityQueue.Response.AllOrderResponse;
import com.saikat.JoePriorityQueue.JoePriorityQueue.Response.QueuePositionResponse;
import com.saikat.JoePriorityQueue.JoePriorityQueue.Response.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * @author Saikat
 */

@RestController
public class QueueService {

    @Autowired
    OrderRepository orderRepository;

    @PostMapping(value = "/insert-order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insertOrder(@RequestBody ObjectNode order){
        RestResponse newResponse = new RestResponse("/insert-order");
        try {
            Integer clientId = Integer.parseInt(order.get("clientId").asText());
            Integer Quantity = Integer.parseInt(order.get("Quantity").asText());

            if(clientId < Constants.CLIENT_ID_MIN || clientId > Constants.CLIENT_ID_MAX){
                newResponse.setMessage("Invalid range of client ID, client ID needs be in range of 1-20000");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(newResponse);
            }

            Integer neworderID = orderRepository.insertOrder(clientId, Quantity);

            if (neworderID == 0) {
                newResponse.setMessage("Duplicate Client ID in the request");
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(newResponse);
            }
            newResponse.setMessage("New Order Inserted in the queue with order ID :" + neworderID);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(newResponse);
        }catch (NumberFormatException e){
            newResponse.setMessage("Improper request format; new order should be in the format of " +
                    "{" +
                    "clientId : <Integer><clientId>," +
                    "Quantity : <Integer><Quantity>" +
                    "}");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(newResponse);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Improper Request");
        }
    }

    @GetMapping(value = "/queue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getQueue(){

        SortedMap<Order,Order> queue = orderRepository.getPriorityQueue();

        AllOrderResponse response = new AllOrderResponse();
        if(queue.size() == 0){
            throw new EmptyQueueException("The queue is currently empty");
        }
        response.setTotalWaitTime(queue.size()+" SECONDS");
        response.setOrders(queue);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(value = "/check-queue-position", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkQueuePosition(@RequestBody ObjectNode request) {

        Integer ClientID = Integer.parseInt(request.get("clientId").asText());
        Integer waitTime = orderRepository.checkWaitTime(ClientID);
        QueuePositionResponse restResponse = new QueuePositionResponse(request.get("clientId").asText());
        if (waitTime != 0) {
            restResponse.setQueuePosition(waitTime + "");
            restResponse.setWaitTime(waitTime+" SECONDS");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(restResponse);
        } else {
            throw new EmptyQueueException("Improper Request, the queue is empty");
        }
    }


    @DeleteMapping(value = "/cancel-order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity cancelOrder(@RequestBody ObjectNode request){
        Integer ClientID = Integer.parseInt(request.get("clientId").asText());
        RestResponse restResponse = new RestResponse("/cancel-order");
        if(orderRepository.cancelOrder(ClientID)){
            restResponse.setMessage("Deleted Successfully");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(restResponse);
        }
        else
            throw new EmptyQueueException("Improper Deletion Request");
    }


    @GetMapping(value = "/delievery", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity nextDelievery(){
        Map<Order,Order> delieveryQueue = orderRepository.nextDelievery();

       if(delieveryQueue.size() == 0){
            throw new EmptyQueueException("No orders avaliable in queue where the total mounts to "+Constants.MAXIMUM_DELIEVERY_QUEUE_ITEMS);
       }
       else {
           AllOrderResponse response = new AllOrderResponse();

           response.setTotalWaitTime(delieveryQueue.size()+" SECONDS");
           response.setOrders(delieveryQueue);
           return ResponseEntity.status(HttpStatus.OK)
                   .body(response);
       }
    }

}
