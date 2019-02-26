package com.paul.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
@RequiredArgsConstructor
public class MyService {

  private final ServiceProperties serviceProperties;


  public String message() {
//    def build = com.paul.library.payload.CarDto.builder()
//            .brand("Tesla")
//            .releasedIn(LocalDate.now())
//            .model("2.1")
//            .build()
    return this.serviceProperties.getMessage();
  }
}
