package com.project.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

/**
 * Created by rayanegouda on 10/06/2018.
 */
public class UserDeSerealizer implements Deserializer<User> {
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }
    @Override
    public User deserialize(String topic, byte[] data) {
      if (data==null){
         return null;
      }
        else {
          try {
              return (User) OBJECT_MAPPER.readValue(data, User.class);
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
        return null;
    }

    @Override
    public void close() {

    }
}
