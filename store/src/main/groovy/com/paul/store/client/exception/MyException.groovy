package com.paul.store.client.exception

class MyException extends RuntimeException {
    MyException(String message) {
        super(message)
    }

    MyException(String message, Throwable cause) {
        super(message, cause)
    }

    MyException(Throwable cause) {
        super(cause)
    }
}
