package com.reloadly.accountservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.lang.reflect.Type;

public class AppUtil {

    private static ObjectMapper mapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        return mapper;
    }

    public static String toJson(Type type) {
        try {
            return mapper().writeValueAsString(type);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error occurred serializing object to json string, error => " + e.getMessage());
        }
    }

    public static <T> String toJson(T t) {
        try {
            return mapper().writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error occurred serializing object to json string, error => " + e.getMessage());
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper().readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error occurred deserializing object to json string: " + e.getMessage());
        }
    }
}
