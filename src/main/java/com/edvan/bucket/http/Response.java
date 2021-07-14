package com.edvan.bucket.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class Response {

    public ResponseEntity<?> success(String message, HttpStatus status, ObjectNode content) {
        ObjectNode data = new ObjectMapper().createObjectNode();

        data.put("message", message);

        if (content != null) {
            data.putPOJO("content", content);
        }

        return new ResponseEntity<>(data, status);
    }

    public ResponseEntity<?> error(String message) {
        ObjectNode data = new ObjectMapper().createObjectNode();

        data.put("message", message);

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
