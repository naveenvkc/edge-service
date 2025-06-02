//package com.polarbookshop.edgeservice.config.session;
//
//import com.polarbookshop.edgeservice.rest.Employee;
//import com.polarbookshop.edgeservice.util.RedisUtil;
//import io.lettuce.core.ClientOptions;
//import io.lettuce.core.SocketOptions;
//import io.lettuce.core.resource.ClientResources;
//import io.lettuce.core.resource.DefaultClientResources;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.ReactiveRedisOperations;
//import org.springframework.data.redis.core.ReactiveRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.session.ReactiveSessionRepository;
//import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;
//import org.springframework.context.annotation.Bean;
//
//import java.time.Duration;
//
//@Configuration(proxyBeanMethods = false)
//@EnableRedisWebSession(maxInactiveIntervalInSeconds = 600)
//@Profile({"redistest"})
//public class RedisConfig {
//
//    private final RedisProperties redisProperties;
//
//    public RedisConfig(RedisProperties redisProperties) {
//        this.redisProperties = redisProperties;
//    }
//
//    @Bean
//    public ClientResources clientResources() {
//        return DefaultClientResources.create();
//    }
////
////    @Bean
////    @Primary
////    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(ClientResources clientResources) {
////        return RedisUtil.getLettuceConnectionFactory(clientResources, redisProperties);
////    }
////
////
////    @Bean
////    @Primary
////    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(
////            ReactiveRedisConnectionFactory connectionFactory) {
////        return RedisUtil.getReactiveRedisTemplate(connectionFactory);
////    }
//
//
//    @Bean
//    @Primary
//    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(ClientResources clientResources) {
//        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
//        redisConfig.setHostName(redisProperties.host());
//        redisConfig.setPort(redisProperties.port());
//        //redisConfig.setPassword(RedisPassword.of(redisProperties.getPrimaryKey()));
//        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//                .clientResources(clientResources)
//                .clientOptions(ClientOptions.builder()
//                        .socketOptions(SocketOptions.builder().connectTimeout(Duration.ofMillis(60000)).build())
//                        .build())
//                //.useSsl()
//                .build();
//        return new LettuceConnectionFactory(redisConfig, clientConfig);
//    }
//
//    @Bean
//    @Primary
//    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(
//            ReactiveRedisConnectionFactory connectionFactory) {
//        return RedisUtil.getReactiveRedisTemplate(connectionFactory);
//    }
//}
