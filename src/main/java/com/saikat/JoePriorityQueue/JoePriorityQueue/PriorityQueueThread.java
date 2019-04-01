package com.saikat.JoePriorityQueue.JoePriorityQueue;


/**
 * @author Saikat
 */
public class PriorityQueueThread implements Runnable {

    OrderRepository orderRepository;

    public PriorityQueueThread(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @Override
    public void run() {
        orderRepository.updateTheMaps();
    }
}
