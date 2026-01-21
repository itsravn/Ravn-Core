package dev.ravn.core.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * Singleton RedisManager handling Jedis connection pooling and Pub/Sub.
 */
public class RedisManager {

    private static RedisManager instance;
    private JedisPool jedisPool;
    private final Logger logger = Logger.getLogger("RedisManager");

    private RedisManager() {
    }

    public static synchronized RedisManager getInstance() {
        if (instance == null) {
            instance = new RedisManager();
        }
        return instance;
    }

    /**
     * Initialize Redis connection pool.
     *
     * @param host     Redis host
     * @param port     Redis port
     * @param password Redis password (can be null)
     */
    public void initialize(String host, int port, String password) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(16);
        config.setMaxIdle(8);
        config.setMinIdle(4);

        if (password != null && !password.isEmpty()) {
            this.jedisPool = new JedisPool(config, host, port, 5000, password);
        } else {
            this.jedisPool = new JedisPool(config, host, port, 5000);
        }
        logger.info("Redis connection pool initialized.");
    }

    /**
     * Get a resource from the pool.
     * Try-with-resources should be used with this.
     */
    public Jedis getResource() {
        if (jedisPool == null) {
            throw new IllegalStateException("Jedis Pool is not initialized!");
        }
        return jedisPool.getResource();
    }

    /**
     * Publish a message to a channel.
     *
     * @param channel Target channel
     * @param message Message content
     */
    public void publish(String channel, String message) {
        try (Jedis jedis = getResource()) {
            jedis.publish(channel, message);
        } catch (Exception e) {
            logger.severe("Failed to publish to Redis: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Subscribe to a channel asynchronously.
     *
     * @param channel    Channel to subscribe to
     * @param subscriber JedisPubSub implementation
     */
    public void subscribe(String channel, JedisPubSub subscriber) {
        CompletableFuture.runAsync(() -> {
            try (Jedis jedis = getResource()) {
                jedis.subscribe(subscriber, channel);
            } catch (Exception e) {
                logger.severe("Redis subscription error on channel " + channel + ": " + e.getMessage());
            }
        });
    }

    /**
     * Close the Redis pool.
     */
    public void closePool() {
        if (jedisPool != null && !jedisPool.isClosed()) {
            jedisPool.close();
            logger.info("Redis connection pool closed.");
        }
    }
}
