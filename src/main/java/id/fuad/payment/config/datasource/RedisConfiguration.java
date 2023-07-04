package id.fuad.payment.config.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
    @Autowired
    Environment environment;

    private JedisConnectionFactory buildConnection(Integer db) {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(environment.getRequiredProperty("config.redis.host"));
        standaloneConfiguration.setPort(environment.getRequiredProperty("config.redis.port", Integer.class));
        standaloneConfiguration.setPassword(environment.getRequiredProperty("config.redis.password"));
        standaloneConfiguration.setDatabase(db);

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(standaloneConfiguration);
        connectionFactory.afterPropertiesSet();

        return connectionFactory;
    }

    @Bean
    StringRedisTemplate redisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(
                buildConnection(environment.getRequiredProperty("config.redis.database.default", Integer.class))
        );
        stringRedisTemplate.setStringSerializer(new StringRedisSerializer());

        return stringRedisTemplate;
    }
}
