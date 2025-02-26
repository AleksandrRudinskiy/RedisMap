package org.redis;

import org.redis.service.MapIntoRedis;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class MapIntoRedisTest {
    private final MapIntoRedis mapIntoRedis = new MapIntoRedis();

    @Test
    public void putTestShouldReturnNull() {
        mapIntoRedis.clear();
        var actual = mapIntoRedis.put("k1", "val1");
        assertNull(actual, "Метод  put должен возвращать null, " +
                "если ключа еще нет");
    }

    @Test
    public void putTestShouldReturnOldValue() {
        mapIntoRedis.clear();
        mapIntoRedis.put("k1", "val1");
        var actual = mapIntoRedis.put("k1", "val200");
        assertEquals("val1", actual,
                "Метод put должен возвращать предыдущее значение");
    }

    @Test
    public void shouldReturnSize2() {
        mapIntoRedis.clear();
        mapIntoRedis.put("k1", "val1");
        mapIntoRedis.put("k2", "val2");
        assertEquals(mapIntoRedis.size(), 2,
                "Количество записей должно быть 2");
    }

    @Test
    public void shouldReturnSize0() {
        mapIntoRedis.clear();
        assertEquals(mapIntoRedis.size(), 0,
                "Количество записей должно быть 2");
    }

    @Test
    public void shouldReturnFalseWhenNotEmpty() {
        mapIntoRedis.clear();
        mapIntoRedis.put("k1", "val1");
        assertFalse(mapIntoRedis.isEmpty(),
                "Должен быть ответ false");
    }

    @Test
    public void shouldReturnTrueWhenMapIsEmpty() {
        mapIntoRedis.clear();
        assertTrue(mapIntoRedis.isEmpty(),
                "Должен быть ответ true");
    }
}
