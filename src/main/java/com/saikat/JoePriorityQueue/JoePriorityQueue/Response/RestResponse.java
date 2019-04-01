package com.saikat.JoePriorityQueue.JoePriorityQueue.Response;

/**
 * @author Saikat
 */
public class RestResponse {

    private String endpoint;
    private String Message;

    public RestResponse(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public String toString() {
        return "RestResponse{" +
                "endpoint='" + endpoint + '\'' +
                ", Message='" + Message + '\'' +
                '}';
    }
}
