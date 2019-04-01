package com.saikat.JoePriorityQueue.JoePriorityQueue;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Saikat
 */
public class Order implements Comparable<Order>, Serializable {
    @NotNull
    @Size(min = 1, max = 20000, message = "ClientID must be in the range of 1-20000")
    private Integer OrderId;

    private Integer ClientId;

    private Integer Quantity;

    private LocalDateTime OrderTime;

    private AtomicLong orderAddedTime = new AtomicLong(0);;

    private boolean isPremium = false;

    public Integer getOrderId() {
        return OrderId;
    }

    private String orderTimeString;

    public Order(int ClientId, int Quantity, int OrderID){
        this.ClientId=ClientId;
        this.Quantity=Quantity;
        LocalDateTime now = LocalDateTime.now();
        this.OrderTime=now;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm:ss");
        this.orderTimeString = dateTimeFormatter.format(now);
        if(ClientId < 1000){
            this.isPremium =true;
        }
        this.OrderId = OrderID;
        this.orderAddedTime.set(System.currentTimeMillis());
    }

    public Long getOrderAddedTime() {
        return orderAddedTime.get();
    }

    public boolean isPremium() {
        return isPremium;
    }

    public LocalDateTime getOrderTime() {
        return OrderTime;
    }

    public String getClientId() {
        return ClientId+" ";
    }

    public Integer getClientIdint(){
        return ClientId;
    }

    public void setClientId(int clientId) {
        ClientId = clientId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "OrderId=" + OrderId +
                ", ClientId=" + ClientId +
                ", Quantity=" + Quantity +
                ", orderAddedTime=" + orderAddedTime +
                ", isPremium=" + isPremium +
                '}';
    }

    @Override
    public int compareTo(Order o) {
        Order newOrder =(Order)o;
        if(this.isPremium && !newOrder.isPremium){
            return -1;
        }
        else if(!this.isPremium && newOrder.isPremium){
            return 1;
        }
        else {
            long order1Time = System.currentTimeMillis()-this.getOrderAddedTime();
            long order2Time = System.currentTimeMillis()-newOrder.getOrderAddedTime();
            //System.out.println((int) (order2Time - order1Time));
            return (int) (order2Time - order1Time);
        }
    }

    @Override
    public int hashCode() {
        System.out.println("In hashcode");
        final int prime = 31;
        int result = 1;
        result = prime * result +  getClientId().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("In equals");
        if (obj instanceof Order) {
            Order pp = (Order) obj;
            return (pp.ClientId.equals(this.ClientId));
        } else {
            return false;
        }
    }
}
