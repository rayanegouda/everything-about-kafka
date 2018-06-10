package com.project.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

/**
 * Created by rayanegouda on 10/06/2018.
 */
public class CarDeSerealizer implements Deserializer<Car> {
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }
    @Override
    public Car deserialize(String topic, byte[] data) {
      if (data==null){
         return null;
      }
        else {
          try {
              return (Car) OBJECT_MAPPER.readValue(data, Car.class);
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
