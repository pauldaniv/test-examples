package com.paul.library.service

import com.paul.library.payload.CarDto
import com.paul.library.payload.OrderDto
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service

import java.time.LocalDate;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class MyService {

  private final ServiceProperties serviceProperties;

  public MyService(ServiceProperties serviceProperties) {
    this.serviceProperties = serviceProperties;
  }

  public String message() {
    def build = CarDto.builder()
            .brand("Tesla")
            .releasedIn(LocalDate.now())
            .model("2.1")
            .build()
    return this.serviceProperties.getMessage();
  }
}
