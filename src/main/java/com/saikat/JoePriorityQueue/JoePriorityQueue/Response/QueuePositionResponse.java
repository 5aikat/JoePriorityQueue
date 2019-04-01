package com.saikat.JoePriorityQueue.JoePriorityQueue.Response;

/**
 * @author Saikat
 */
public class QueuePositionResponse {

    private String clientId;
    private String queuePosition;
    private String waitTime;

    public QueuePositionResponse(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(String queuePosition) {
        this.queuePosition = queuePosition;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }
}
