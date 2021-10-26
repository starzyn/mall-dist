package com.codezzz.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class JsonMapper {
    private static final ObjectMapper NON_EMPTY;
    private static final ObjectMapper NON_DEFAULT;
    private static final ObjectMapper ALWAYS;
    private static final ObjectMapper NON_NULL;

    public JsonMapper() {
    }

    public static String toNonEmptyJson(Object object) {
        try {
            return NON_EMPTY.writeValueAsString(object);
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static String toNonDefaultJson(Object object) {
        try {
            return NON_DEFAULT.writeValueAsString(object);
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static String toAlwaysJson(Object object) {
        try {
            return ALWAYS.writeValueAsString(object);
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static String toNonNullJson(Object object) {
        try {
            return NON_NULL.writeValueAsString(object);
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        try {
            return ALWAYS.readValue(jsonString, clazz);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static <T> T fromJson(String jsonString, JavaType javaType) {
        try {
            return ALWAYS.readValue(jsonString, javaType);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> elementClass) {
        return (List)fromJson(json, constructParametricType(List.class, elementClass));
    }

    public static <K, V> Map<K, V> fromJsonToMap(String json, Class<K> keyClass, Class<V> valueClass) {
        return (Map)fromJson(json, constructParametricType(Map.class, keyClass, valueClass));
    }

    public static Map<String, Object> fromJsonToMap(String json) {
        return (Map)fromJson(json, constructParametricType(Map.class, String.class, Object.class));
    }

    public static JavaType constructParametricType(Class<?> parametrized, Class<?>... elementClasses) {
        return ALWAYS.getTypeFactory().constructParametricType(parametrized, elementClasses);
    }

    public static ObjectMapper newObjectMapper(JsonInclude.Include include) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(include);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setTimeZone(TimeZone.getDefault());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        mapper.registerModule(new GuavaModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        registerCustomModule(mapper);
        return mapper;
    }

    public static void registerCustomModule(ObjectMapper mapper) {
        SimpleModule module = new SimpleModule();
//        module.addDeserializer(LocalDateTime.class, new JsonMapper.CustomLocalDateTimeDeserializer(DateTimes.COMMON_DATE_TIME.getFormatter()));
//        module.addDeserializer(LocalDate.class, new JsonMapper.CustomLocalDateDeserializer(DateTimes.COMMON_DATE.getFormatter()));
//        module.addDeserializer(LocalTime.class, new JsonMapper.CustomLocalTimeDeserializer(DateTimes.COMMON_TIME.getFormatter()));
//        module.addDeserializer(YearMonth.class, new JsonMapper.CustomYearMonthDeserializer(DateTimes.CHN_MONTH.getFormatter()));
//        module.addDeserializer(MonthDay.class, new JsonMapper.CustomMonthDayDeserializer(DateTimes.CHN_MONTH_DATE.getFormatter()));
        mapper.registerModule(module);
    }

    static {
        NON_EMPTY = newObjectMapper(JsonInclude.Include.NON_EMPTY);
        NON_DEFAULT = newObjectMapper(JsonInclude.Include.NON_DEFAULT);
        ALWAYS = newObjectMapper(JsonInclude.Include.ALWAYS);
        NON_NULL = newObjectMapper(JsonInclude.Include.NON_NULL);
    }

    private static class CustomMonthDayDeserializer extends MonthDayDeserializer {
        private static final long serialVersionUID = 6988597109082367232L;
        private final DateTimeFormatter formatterOnFailure;

        private CustomMonthDayDeserializer(DateTimeFormatter formatterOnFailure) {
            super((DateTimeFormatter)null);
            this.formatterOnFailure = formatterOnFailure;
        }

        public MonthDay deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            try {
                return super.deserialize(parser, context);
            } catch (IOException var4) {
                return MonthDay.parse(parser.getText().trim(), this.formatterOnFailure);
            }
        }
    }

    private static class CustomYearMonthDeserializer extends YearMonthDeserializer {
        private static final long serialVersionUID = 707238372357810030L;
        private final DateTimeFormatter formatterOnFailure;

        private CustomYearMonthDeserializer(DateTimeFormatter formatterOnFailure) {
            super(DateTimeFormatter.ofPattern("uuuu-MM"));
            this.formatterOnFailure = formatterOnFailure;
        }

        public YearMonth deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            try {
                return super.deserialize(parser, context);
            } catch (IOException var4) {
                return YearMonth.parse(parser.getText().trim(), this.formatterOnFailure);
            }
        }
    }

    private static class CustomLocalTimeDeserializer extends LocalTimeDeserializer {
        private static final long serialVersionUID = 7818627100921395338L;
        private final DateTimeFormatter formatterOnFailure;

        private CustomLocalTimeDeserializer(DateTimeFormatter formatterOnFailure) {
            super(DateTimeFormatter.ISO_LOCAL_TIME);
            this.formatterOnFailure = formatterOnFailure;
        }

        public LocalTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            try {
                return super.deserialize(parser, context);
            } catch (IOException var5) {
                String string = parser.getText().trim();
                return LocalTime.parse(string, this.formatterOnFailure);
            }
        }
    }

    private static class CustomLocalDateDeserializer extends LocalDateDeserializer {
        private static final long serialVersionUID = 3218254419776254843L;
        private final DateTimeFormatter formatterOnFailure;

        private CustomLocalDateDeserializer(DateTimeFormatter formatterOnFailure) {
            super(DateTimeFormatter.ISO_LOCAL_DATE);
            this.formatterOnFailure = formatterOnFailure;
        }

        public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            try {
                return super.deserialize(parser, context);
            } catch (IOException var5) {
                String string = parser.getText().trim();
                return LocalDate.parse(string, this.formatterOnFailure);
            }
        }
    }

    private static class CustomLocalDateTimeDeserializer extends LocalDateTimeDeserializer {
        private static final long serialVersionUID = -2007179451387522834L;
        private final DateTimeFormatter formatterOnFailure;

        private CustomLocalDateTimeDeserializer(DateTimeFormatter formatterOnFailure) {
            super(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            this.formatterOnFailure = formatterOnFailure;
        }

        public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            try {
                return super.deserialize(parser, context);
            } catch (IOException var5) {
                String string = parser.getText().trim();
                return LocalDateTime.parse(string, this.formatterOnFailure);
            }
        }
    }
}
