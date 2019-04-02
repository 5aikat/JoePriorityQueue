package com.saikat.JoePriorityQueue.JoePriorityQueue;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author Saikat
 */
@Component
public class OrderRepository {

    Map<Integer,Order> orderPriorityQueue;

    SortedMap<Order,Order> sortedPriorityQueue;

    int baseOrderID=1000;

    public OrderRepository(){
        this.orderPriorityQueue = new ConcurrentHashMap<>();
        this.sortedPriorityQueue = new ConcurrentSkipListMap<>();
        startJoeScheduledThread();
    }


    public Integer insertOrder(int clientID, int quantity){
        if(orderPriorityQueue.containsKey(clientID)){
            return 0;
        }
        Order order = new Order(clientID,quantity,++baseOrderID);
        orderPriorityQueue.put(clientID,order);
        return order.getOrderId();
    }

    public SortedMap<Order,Order> getPriorityQueue(){

        updateTheMaps();

        return  sortedPriorityQueue;
    }

    public void displayPriorityQueue() {

        updateTheMaps();

        System.out.println(sortedPriorityQueue);

        System.out.println(orderPriorityQueue);
    }

    public Integer checkWaitTime(Integer ClientId){

        updateTheMaps();

        if(orderPriorityQueue.size() == 0){
            return 0;
        }

        Order order = orderPriorityQueue.get(ClientId);
        Integer waitTIme = sortedPriorityQueue.headMap(order).size();

        return waitTIme*Constants.WAIT_TIME_APROXIMATION+1;
    }

    public Boolean cancelOrder(Integer ClientId){

        if(orderPriorityQueue.remove(ClientId)!= null){
            updateTheMaps();
            return true;
        }
        updateTheMaps();
        return false;
    }

    public Map<Order, Order> nextDelievery(){
        updateTheMaps();

        Set<Order> keys = sortedPriorityQueue.keySet();

        Iterator iterator = keys.iterator();

        Map<Order,Order> nextDelieveryQueue = new TreeMap<>();

        Integer totalItems =0;

        while(iterator.hasNext()){
            Order obj = (Order)iterator.next();
            if(totalItems+ obj.getQuantity()<=Constants.MAXIMUM_DELIEVERY_QUEUE_ITEMS ){
                totalItems+=obj.getQuantity();
                nextDelieveryQueue.put(obj,obj);
            }
        }
        popElementsFromMap(nextDelieveryQueue);
        return nextDelieveryQueue;
    }

    public void updateTheMaps(){
        System.out.println("Thread :"+Thread.currentThread().getName());
        Set<Integer> Keys = orderPriorityQueue.keySet();
        sortedPriorityQueue.clear();
        for(Integer k : Keys){
            Order order = orderPriorityQueue.get(k);
            sortedPriorityQueue.put(order,order);
        }
    }

    public void popElementsFromMap(Map<Order,Order> delieveryMap){

        Set<Order> keys = delieveryMap.keySet();
        for(Order order: keys){
            if(orderPriorityQueue.containsKey(order.getClientIdint())){
                orderPriorityQueue.remove(order.getClientIdint());
            }
        }
        updateTheMaps();
    }

    private void startJoeScheduledThread(){


        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

        scheduledExecutorService.scheduleAtFixedRate(new PriorityQueueThread(this),0,Constants.SCHEDULE_PERIOD_OF_JOE_THREAD_IN_MINUTES,TimeUnit.MINUTES);
    }

}
