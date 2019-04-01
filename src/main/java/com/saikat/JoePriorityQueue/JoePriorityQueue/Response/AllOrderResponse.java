package com.saikat.JoePriorityQueue.JoePriorityQueue.Response;

import com.saikat.JoePriorityQueue.JoePriorityQueue.Order;

import java.util.Map;

/**
 * @author Saikat
 */
public class AllOrderResponse {

    private String totalWaitTime;
    private Map<Order,Order> orders;

    public AllOrderResponse(){

    }

    public String getTotalWaitTime() {
        return totalWaitTime;
    }

    public void setTotalWaitTime(String totalWaitTime) {
        this.totalWaitTime = totalWaitTime;
    }

    public Map<Order, Order> getOrders() {
        return orders;
    }

    public void setOrders(Map<Order, Order> orders) {
        this.orders = orders;
    }
}
