package com.paul.store.client.exception

import feign.Response
import feign.codec.ErrorDecoder
import org.hibernate.ObjectNotFoundException

class NotFoundAwareDecoder implements ErrorDecoder {


  @Override
  Exception decode(String methodKey, Response response) {
    if (response.status() == 401) {
      throw new ObjectNotFoundException(1, "test")
    }
    return new Exception("Error: " + response.status())
  }
}
