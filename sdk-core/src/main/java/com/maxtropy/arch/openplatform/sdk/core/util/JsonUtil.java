package com.maxtropy.arch.openplatform.sdk.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyang
 */
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString("");
            }
        });
    }

    private JsonUtil() {
    }

    public static String bean2JsonStr(Object bean) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(bean);
    }

    public static JsonNode bean2JsonNode(Object bean) throws IOException {
        String json = bean2JsonStr(bean);
        return jsonStr2JsonNode(json);
    }

    public static JsonNode jsonStr2JsonNode(String json) throws IOException {
        return OBJECT_MAPPER.readTree(json);
    }

    public static <T> T jsonNode2Bean(JsonNode jsonNode, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(jsonNode.traverse(), clazz);
    }

    public static <T> T jsonStr2Bean(String json, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    public static <T> List<T> jsonStr2BeanList(String json, Class<T> clazz) throws IOException {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory()
                .constructParametricType(ArrayList.class, clazz);
        return OBJECT_MAPPER.readValue(json, javaType);
    }

    public static String formatJson(Object bean) {
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json解析失败", e);
        }
    }


    public static ObjectNode createObjectNode() {
        return OBJECT_MAPPER.createObjectNode();
    }

}
