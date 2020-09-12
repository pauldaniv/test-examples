package com.pauldaniv.store.exception.handling;

import org.hibernate.service.spi.ServiceException;

import java.lang.reflect.Constructor;

public class ThrownServiceExceptionDetails {
    private Class<? extends ServiceException> clazz;
    private Constructor<? extends ServiceException> emptyConstructor;
    private Constructor<? extends ServiceException> messageConstructor;

    ThrownServiceExceptionDetails withClazz(Class<? extends ServiceException> clazz) {
        this.clazz = clazz;
        return this;
    }

    ThrownServiceExceptionDetails withEmptyConstructor(Constructor<? extends ServiceException> emptyConstructor) {
        this.emptyConstructor = emptyConstructor;
        return this;
    }

    ThrownServiceExceptionDetails withMessageConstructor(Constructor<? extends ServiceException> messageConstructor) {
        this.messageConstructor = messageConstructor;
        return this;
    }
}
