package cn.ctcc.token.server.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: zk
 * @Date: 2019/2/28 18:15
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Configuration
public class RedisConfig {

    /**
     *Redis书库据索引(默认为0)
     */
    @Value("${spring.redis.database}")
    private int database;

    /**
     *Redis服务器ip
     */
    @Value("${spring.redis.host}")
    private String host;

    /**
     *Redis服务端口号(默认6379)
     */
    @Value("${spring.redis.port}")
    private int port;

    /**
     *Redis认证密码(默认为空)
     */
    @Value("${spring.redis.password}")
    private String password;

    /**
     *连接超时时间(毫秒)
     */
    @Value("${spring.redis.timeout}")
    private Long timeOut;

    /**
     *Redis集群的节点
     */
//    @Value("${spring.redis.cluster.nodes}")
//    private String nodes;

    /**
     *在群集中执行命令时要遵循的最大重定向数目
     */
//    @Value("${spring.redis.cluster.max-redirects}")
//    private int maxRedirects;

    /**
     *Jedis连接池的最大连接数(使用负数表示没限制)
     */
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    /**
     *Jedis连接池最大阻塞时间(使用负数表示没限制)
     */
    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWait;

    /**
     *Jedis连接池最大空闲连接
     */
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    /**
     *Jedis连接池最小空闲连接
     */
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Bean
    public JedisPoolConfig jedisPoolConfig(){

        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWait);

        return config;
    }

//
//    @Bean
//    public RedisClusterConfiguration redisClusterConfiguration(){
//        RedisClusterConfiguration configuration = new RedisClusterConfiguration(Arrays.asList(nodes));
//        configuration.setMaxRedirects(maxRedirects);
//
//        return configuration;
//    }


    /**
     * JedisConnectionFactory
     * 如果参数类型所对应的实例在spring容器中只有一个，则默认选择这个实例。如果有多个，则需要根据参数名称来作为bean名来选择
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(/*RedisClusterConfiguration redisClusterConfiguration,*/ JedisPoolConfig jedisPoolConfig){


        RedisStandaloneConfiguration redisStandaloneConfiguration =new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        redisStandaloneConfiguration.setPort(port);

        //设置redis的服务的端口号
        //获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        //指定jedisPoolConifig来修改默认的连接池构造器（真麻烦，滥用设计模式！）
        jpcb.poolConfig(jedisPoolConfig);
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        //单机配置 + 客户端配置 = jedis连接工厂
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    /**
     * redis序列化设置
     */
    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(){

        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);

        return serializer;
    }


    /**
     * 配置RedisTemplate
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory,Jackson2JsonRedisSerializer jackson2JsonRedisSerializer){

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);

        //设置key的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        //设置value的序列化方式
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        //判断是否设置完毕
        redisTemplate.afterPropertiesSet();


        return redisTemplate;
    }




}
