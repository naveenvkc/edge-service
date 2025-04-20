package com.polarbookshop.edgeservice.rest;

import com.polarbookshop.edgeservice.model.EmptyDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Profile({"redistest"})
public class RedisTestController {

    private final ReactiveRedisTemplate<String, Object>  redisTemplate;
    private ReactiveValueOperations<String, Object> reactiveValueOps;

    public RedisTestController(ReactiveRedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @RequestMapping(value = "/redissession/set", produces = {"application/json"}, method = RequestMethod.GET)
    public Mono<Boolean> set(){
        reactiveValueOps = redisTemplate.opsForValue();
        return reactiveValueOps.set("123",
                new Employee("1", "Bill", "Accounts"));
    }

    @RequestMapping(value = "/redissession/get", produces = {"application/json"}, method = RequestMethod.GET)
    public Mono<Object> get(){
        reactiveValueOps = redisTemplate.opsForValue();
        return reactiveValueOps.get("123");
    }
}
