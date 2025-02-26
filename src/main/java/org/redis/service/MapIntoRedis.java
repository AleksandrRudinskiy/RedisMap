package org.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

/**
 * Класс-прослойка между Redis и Map
 */
@RequiredArgsConstructor
@Slf4j
public class MapIntoRedis implements Map<String, String> {

    /**
     * Экземпляр класса JedisPool присоединённый к хосту "localhost" на пору 6379
     */
    private final JedisPoolConfig poolConfig = new JedisPoolConfig();
    private final JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379);
    private final Jedis jedis = jedisPool.getResource();

    /**
     * Метод возвращает количество записей в БД
     *
     * @return int количество записей в БД
     */
    @Override
    public int size() {
        return jedis.keys("*").size();
    }

    /**
     * Дает информацию о том пуста ли БД или нет
     *
     * @return true если пуста иначе false
     */
    @Override
    public boolean isEmpty() {
        return jedis.keys("*").isEmpty();
    }

    /**
     * Метод позволяет проверить наличие значения по ключу
     *
     * @param key ключ типа String
     * @return true если присутствует значение иначе false
     */
    @Override
    public boolean containsKey(Object key) {
        return jedis.get(key.toString()) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            String currentValue = jedis.get(key);
            if (currentValue.equals(value.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод для получения значения по ключу
     *
     * @param key ключ типа String
     * @return String тип возвращаемого значения
     */
    @Override
    public String get(Object key) {
        return jedis.get(key.toString());
    }

    /**
     * Метод для записи в БД значение по его ключу
     *
     * @param key   ключ типа String
     * @param value значение типа String
     * @return возвращаемое значение типа String
     */
    @Override
    public String put(String key, String value) {
        if (!containsKey(key)) {
            jedis.set(key, value);
            return null;
        } else {
            String oldValue = jedis.get(key);
            jedis.set(key, value);
            return oldValue;
        }
    }

    /**
     * Метод удаляет запись из БД по ключу
     *
     * @param key ключ типа String
     * @return String тип возвращаемого значения.
     * Возвращает значение если ключ находился в БД и null если ключ отсутствовал
     */
    @Override
    public String remove(Object key) {
        if (containsKey(key)) {
            String value = get(key);
            jedis.del(key.toString());
            return value;
        } else {
            return null;
        }
    }

    /**
     * Метод позволяет положить Map в БД
     *
     * @param m экземпляр Map
     */
    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        Set<?> keys = m.keySet();
        for (Object key : keys) {
            jedis.set(key.toString(), m.get(key.toString()));
        }
    }

    /**
     * Метод служит для очистки БД
     */
    @Override
    public void clear() {
        jedis.flushDB();
    }

    @Override
    public Set<String> keySet() {
        return new HashSet<>(jedis.keys("*"));
    }

    @Override
    public Collection<String> values() {
        Set<String> keys = new HashSet<>(jedis.keys("*"));
        List<String> values = new ArrayList<>();
        for (String key : keys) {
            String value = jedis.get(key);
            values.add(value);
        }
        return values;
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        Set<Entry<String, String>> result = new HashSet<>();
        Set<String> keys = new HashSet<>(jedis.keys("*"));
        for (String key : keys) {
            String value = jedis.get(key);
            Entry<String, String> entry = new AbstractMap.SimpleEntry<>(key, value);
            result.add(entry);
        }
        return result;
    }
}
