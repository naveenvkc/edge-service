//package com.polarbookshop.edgeservice.util;
//
//import com.polarbookshop.edgeservice.config.session.RedisProperties;
//import com.polarbookshop.edgeservice.rest.Employee;
//import io.lettuce.core.ClientOptions;
//import io.lettuce.core.SocketOptions;
//import io.lettuce.core.resource.ClientResources;
//import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.data.redis.core.ReactiveRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//
//import java.time.Duration;
//
//public class RedisUtil {
//
//    private RedisUtil() {
//        // empty constructor
//    }
//
//    public static @NotNull LettuceConnectionFactory getLettuceConnectionFactory(
//            ClientResources clientResources, RedisProperties redisProperties
//    ) {
//        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
//        redisConfig.setHostName(redisProperties.host());
//        redisConfig.setPort(redisProperties.port());
//        //redisConfig.setPassword(RedisPassword.of(redisProperties.getPrimaryKey()));
//        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//                .clientResources(clientResources)
//                .clientOptions(ClientOptions.builder()
//                        .socketOptions(SocketOptions.builder().connectTimeout(Duration.ofMillis(60000)).build())
//                        .build())
//                .useSsl()
//                .build();
//        return new LettuceConnectionFactory(redisConfig, clientConfig);
//    }
//
//    public static @NotNull ReactiveRedisTemplate<String, Object> getReactiveRedisTemplate(
//            ReactiveRedisConnectionFactory connectionFactory
//    ) {
//        StringRedisSerializer redisSerializer = new StringRedisSerializer();
//        Jackson2JsonRedisSerializer<Object> objectSerializer = new
//                Jackson2JsonRedisSerializer<>(Object.class);
//        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder =
//                RedisSerializationContext.newSerializationContext(
//                redisSerializer);
//
//        RedisSerializationContext<String, Object> context = builder.value(objectSerializer)
//                .hashKey(redisSerializer)
//                .hashValue(objectSerializer)
//                .build();
//
//        return new ReactiveRedisTemplate<>(connectionFactory, context);
//    }
//
//}
