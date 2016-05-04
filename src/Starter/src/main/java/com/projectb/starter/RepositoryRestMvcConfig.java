package com.projectb.starter;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.projectb.starter.serializer.PointDeserializer;
import com.projectb.starter.serializer.PointSerializer;
import com.vividsolutions.jts.geom.Point;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.stereotype.Component;

@Component
public class RepositoryRestMvcConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new SimpleModule() {
            @Override
            public void setupModule(SetupContext context) {
                SimpleSerializers serializers = new SimpleSerializers();
                SimpleDeserializers deserializers = new SimpleDeserializers();

                serializers.addSerializer(Point.class, new PointSerializer());
                deserializers.addDeserializer(Point.class, new PointDeserializer());

                context.addSerializers(serializers);
                context.addDeserializers(deserializers);
            }
        });
    }
}
