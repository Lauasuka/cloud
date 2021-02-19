package io.amoe.cloud.account.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import io.amoe.cloud.constant.GlobalConstant;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Amoe
 * @date 2020/5/15 15:06
 */
@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            // Long serializer to String
            builder.serializerByType(Long.class, ToStringSerializer.instance);

            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(GlobalConstant.DATE_TIME_FORMATTER));
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(GlobalConstant.DATE_TIME_FORMATTER));

            builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(GlobalConstant.DATE_FORMATTER));
            builder.serializerByType(LocalTime.class, new LocalTimeSerializer(GlobalConstant.DATE_FORMATTER));

            builder.serializerByType(Boolean.class, ToStringSerializer.instance);
        };
    }
}
