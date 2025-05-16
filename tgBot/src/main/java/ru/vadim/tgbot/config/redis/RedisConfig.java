package ru.vadim.tgbot.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.vadim.tgbot.dto.response.CategoryDto;

import java.util.List;

@Configuration
public class RedisConfig {
    private final static int REDIS_MAX_REDIRECT = 3;

    @Profile("docker")
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration(List.of(
                "redis-node-1:6379",
                "redis-node-2:6379",
                "redis-node-3:6379",
                "redis-node-4:6379",
                "redis-node-5:6379",
                "redis-node-6:6379"
        ));
        clusterConfig.setMaxRedirects(REDIS_MAX_REDIRECT);

        return new LettuceConnectionFactory(clusterConfig);
    }

    @Bean
    public RedisTemplate<String, CategoryDto> categoryRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, CategoryDto> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    public RedisTemplate<String, List<CategoryDto>> categoryListRedisTemplate(
            RedisConnectionFactory connectionFactory
    ) {
        RedisTemplate<String, List<CategoryDto>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
