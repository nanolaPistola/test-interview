package com.multicert.test.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
public class Common {

    private static SimpleModule emptyStringModule = new SimpleModule()
        .addDeserializer(
            String.class,
            new StdDeserializer<String>(String.class) {
                @Override
                public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                    throws IOException, JsonProcessingException {
                    String result = StringDeserializer.instance.deserialize(jsonParser, deserializationContext);
                    if (StringUtils.isEmpty(result)) {
                        return null;
                    }
                    return result;
                }
            }
        );
    private static final ObjectMapper defaultObjectMapper =new ObjectMapper()
        .registerModule(emptyStringModule)
        .registerModule(new JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);


    /**
     * @param resolver
     * @param <T>
     * @return
     */
    public static <T> Optional<T> resolve(Supplier<T> resolver) {
        try {
            T result = resolver.get();
            return Optional.ofNullable(result);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    public static String objectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JavaTimeModule module = new JavaTimeModule();
            mapper.registerModule(module);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.warn("Common|objectToJson|Exception: [{}]", e.getMessage());
        }
        return null;
    }

    public static Map<String, Object> writeValueAsMap(String value)  {
        if (Objects.nonNull(value)) {
            ObjectMapper objectMapper = defaultObjectMapper;
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            try {
                Map<String, Object> result = objectMapper.readValue(value, new TypeReference<Map>() {});
                return result;
            }  catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
