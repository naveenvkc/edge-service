package com.polarbookshop.edgeservice.rest;

import com.polarbookshop.edgeservice.model.EmptyDataResponse;
import com.polarbookshop.edgeservice.model.Notification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EdgeController {
    private static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";
    private static final String X_FRAME_OPTIONS = "X-Frame-Options";
    private static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";

    @RequestMapping(value = "/catalog-fallback", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<EmptyDataResponse> get(){
        EmptyDataResponse error = new EmptyDataResponse();
        return new ResponseEntity<>(error, getResponseHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/catalog-fallback", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<EmptyDataResponse> post(){
        EmptyDataResponse error = new EmptyDataResponse();
        List<Notification> notifications = new ArrayList<>();
        Notification notification = new Notification();
        notification.setMessage("SERVICE UNAVAILABLE");
        notifications.add(notification);
        error.setNotifications(notifications);
        return new ResponseEntity<>(error, getResponseHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    private HttpHeaders getResponseHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(STRICT_TRANSPORT_SECURITY, "max-age=3153600; includeSubDomains");
        headers.add(X_FRAME_OPTIONS, "DENY");
        headers.add(X_CONTENT_TYPE_OPTIONS, "nosniff");

        return headers;
    }
}
