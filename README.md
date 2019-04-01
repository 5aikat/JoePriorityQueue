# JoePriorityQueue

Endpoints :

1. An endpoint to add a new order to the queue

POST: /insert-order

request:
{
  "clientId" : "25",
  "Quantity" : "1"
}

response:
{
    "endpoint": "/insert-order",
    "message": "New Order Inserted in the queue with order ID :1001"
}

2. An Endpoint for client to check the queue position

GET: /check-queue-position

request:
{
  "clientId" : "25"
}

Response:
{
    "clientId": "25",
    "queuePosition": "1",
    "waitTime": "1 SECONDS"
}

3. An endpoint to see all the entries in queue

GET: /queue

response:
{
    "totalWaitTime": "2 SECONDS",
    "orders": {
        "Order{OrderId=1002, ClientId=25, Quantity=15, orderAddedTime=1554127560818, isPremium=true}": {
            "orderAddedTime": 1554127560818,
            "clientId": "25 ",
            "premium": true,
            "orderTime": "2019-04-01T19:36:00.8187968",
            "quantity": 15,
            "clientIdint": 25,
            "orderId": 1002
        },
        "Order{OrderId=1001, ClientId=1025, Quantity=15, orderAddedTime=1554127554815, isPremium=false}": {
            "orderAddedTime": 1554127554815,
            "clientId": "1025 ",
            "premium": false,
            "orderTime": "2019-04-01T19:35:54.8142859",
            "quantity": 15,
            "clientIdint": 1025,
            "orderId": 1001
        }
    }
}


4. An endpoint to retrive the next delievery to be placed in the cart 

GET: /delievery

response :
{
    "totalWaitTime": "1 SECONDS",
    "orders": {
        "Order{OrderId=1002, ClientId=25, Quantity=15, orderAddedTime=1554127902357, isPremium=true}": {
            "orderAddedTime": 1554127902357,
            "premium": true,
            "clientIdint": 25,
            "clientId": "25 ",
            "quantity": 15,
            "orderTime": "2019-04-01T19:41:42.3579999",
            "orderId": 1002
        }
    }
}

5. An endpoint to cancel the order

DELETE: /cancel-order

request :
{
  "clientId": "25"
}

response:
{
    "endpoint": "/cancel-order",
    "message": "Deleted Successfully"
}
