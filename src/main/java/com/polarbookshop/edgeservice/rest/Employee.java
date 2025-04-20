package com.polarbookshop.edgeservice.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Employee {

    private String id;

    private String name;

    private String dept;
}
