# 1.GRPC - Unary RPC

## What is gRPC Unary?
 - In gRPC, unary RPC is the simplest type of Remote Procedure Call:
    - The client sends one request to the server.
    - The server processes the request. 
    - The server sends back one response to the client.
    - It's like a normal function call but over the network.

## How Unary RPC Works — Step by Step ?

### 1.Client sends a request message:

- The client calls a method on a gRPC stub (client-side object). This method sends a serialized request message to the server.

### 2.Server receives the request:

- The server's service implementation gets the request, deserializes it, and runs the business logic.

### 3.Server sends back a response:

 - After processing, the server serializes the response message and sends it back to the client.

### 4.Client receives the response:

- The client stub receives the response, deserializes it, and returns it to the client app.

## Characteristics of Unary RPC
 - Single request, single response.
 - Communication happens over HTTP/2.
 - Efficient serialization via Protocol Buffers (protobuf).
 - Supports deadlines, cancellation, and metadata.

# 2. gRPC Server Side Streaming RPC

## What is gRPC Server Side Streaming?
 
 - In gRPC, server-side streaming RPC allows the server to send multiple responses to a single client request:
    - The client sends one request to the server.
    - The server processes the request and sends back a stream of responses.
    - The client reads the stream of responses until the server finishes sending.

## How Server Side Streaming RPC Works — Step by Step?

### 1.Client sends a request message:
   - The client calls a method on a gRPC stub, sending a serialized request message to the server.

### 2.Server receives the request:
   - The server's service implementation gets the request, deserializes it, and starts processing.

### 3.Server sends back a stream of responses:
   - The server serializes and sends multiple response messages back to the client as a stream.

### 4.Client reads the stream of responses:
   - The client stub reads the stream of responses, deserializes each message, and processes them one by one until the server finishes sending.

## Characteristics of Server Side Streaming RPC
   - Single request, multiple responses.
   - Communication happens over HTTP/2.
   - Efficient serialization via Protocol Buffers (protobuf).
   - Supports deadlines, cancellation, and metadata.
   - Useful for real-time data updates or large datasets.

# 3. gRPC Client Side Streaming RPC