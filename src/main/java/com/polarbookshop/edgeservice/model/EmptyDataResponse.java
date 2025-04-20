package com.polarbookshop.edgeservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Generated
public class EmptyDataResponse {

    @JsonProperty("data")
    private String data;

    @JsonProperty("notifications")
    private List<Notification> notifications = new ArrayList<>();
}
