package com.paul.store.controller;

import com.paul.library.domen.TestEntity;
import com.paul.store.Client;
import lombok.Data;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RefreshScope
@RestController
public class Controller {

  public Controller(Client client) {
    this.client = client;
  }

  private final Client client;


  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<TestEntity> all() {
    return client.getAll();
  }

  @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
  public String save(Response entity) {
    return client.save(new TestEntity("TestName", "Test last name"));
  }

//  @GetMapping("/")
//  public Response home() {
//    return new Response() {
//      @Override
//      public String getName() {
//        return "response value";
//      }
//
//      @Override
//      public String getValue() {
//        return client.index();
//      }
//    };
//  }



  public class Response {
    private String test1;
    private String test2;

    public Response() {
    }

    public Response setTest1(String test1) {
      this.test1 = test1;
      return this;
    }

    public Response setTest2(String test2) {
      this.test2 = test2;
      return this;
    }
  }

}
