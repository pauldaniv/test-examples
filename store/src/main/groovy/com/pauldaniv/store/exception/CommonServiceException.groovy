package com.pauldaniv.store.exception

class CommonServiceException extends RuntimeException {
  private String errorCode;
  //Constructors omitted
  public String getErrorCode() {
    return errorCode;
  }
}
