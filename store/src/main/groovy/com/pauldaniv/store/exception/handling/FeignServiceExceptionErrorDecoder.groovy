package com.pauldaniv.store.exception.handling


import groovy.util.logging.Slf4j

@Slf4j
class FeignServiceExceptionErrorDecoder {
//
//    private Class<?> apiClass
//    private Map<String, ThrownServiceExceptionDetails> exceptionsThrown = new HashMap<>()
//
//    FeignServiceExceptionErrorDecoder(Class<?> apiClass) throws Exception {
//        this.apiClass = apiClass
//        for (Method method : apiClass.getMethods()) {
//            if (method.getAnnotation(RequestLine) != null) {
//                for (Class<?> clazz : method.getExceptionTypes()) {
//                    if (CommonServiceException.isAssignableFrom(clazz)) {
//                        if (Modifier.isAbstract(clazz.getModifiers())) {
//                            extractServiceExceptionInfoFromSubClasses(clazz)
//                        } else {
//                            extractServiceExceptionInfo(clazz)
//                        }
//                    } else {
//                        log.info("Exception '{}' declared thrown on interface '{}' doesn't inherit from CommonServiceException, it will be skipped.", clazz.getName(), apiClass.getName())
//                    }
//                }
//            }
//        }
//    }
//
//    private void extractServiceExceptionInfo(Class<?> clazz)
//            throws Exception {
//        CommonServiceException thrownException = null
//        Constructor<?> emptyConstructor = null
//        Constructor<?> messageConstructor = null
//
//        for (Constructor<?> constructor : clazz.getConstructors()) {
//            Class<?>[] parameters = constructor.getParameterTypes()
//            if (parameters.length == 0) {
//                emptyConstructor = constructor
//                thrownException = (CommonServiceException) constructor.newInstance()
//            } else if (parameters.length == 1 && parameters[0].isAssignableFrom(String)) {
//                messageConstructor = constructor
//                thrownException = (CommonServiceException) constructor.newInstance(new String())
//            }
//        }
//
//        if (thrownException != null) {
//            exceptionsThrown.put(thrownException.getErrorCode(),
//                    new ThrownServiceExceptionDetails()
//                            .withClazz((Class<? extends CommonServiceException>) clazz)
//                            .withEmptyConstructor((Constructor<? extends CommonServiceException>) emptyConstructor)
//                            .withMessageConstructor((Constructor<? extends CommonServiceException>) messageConstructor))
//        } else {
//            log.warn("Couldn't instantiate the exception '{}' for the interface '{}', it needs an empty or String only * public * constructor.", clazz.getName(), apiClass.getName())
//        }
//    }
//
//    private void extractServiceExceptionInfoFromSubClasses(Class<?> clazz)
//            throws Exception {
//        Set<Class<?>> subClasses = getAllSubClasses(clazz)
//        for (Class<?> subClass : subClasses) {
//            extractServiceExceptionInfo(subClass)
//        }
//    }
//
//    private Set<Class<?>> getAllSubClasses(Class<?> clazz) throws ClassNotFoundException {
//        ClassPathScanningCandidateComponentProvider provider =
//                new ClassPathScanningCandidateComponentProvider(false)
//        provider.addIncludeFilter(new AssignableTypeFilter(clazz))
//
//        Set<BeanDefinition> components = provider.findCandidateComponents("your/base/package/here")
//
//        Set<Class<?>> subClasses = new HashSet<>()
//        for (BeanDefinition component : components) {
//            subClasses.add(Class.forName(component.getBeanClassName()))
//        }
//        return subClasses
//    }
//
//    @Override
//    Exception decode(String methodKey,
//                     Response response) {
//        private JacksonDecoder jacksonDecoder = new JacksonDecoder()
//        try {
//            RestException restException = (RestException) jacksonDecoder.decode(response, RestException)
//            if (restException != null && exceptionsThrown.containsKey(restException.getErrorCode())) {
//                return getExceptionByReflection(restException)
//            }
//        } catch (IOException e) {
//            // Fail silently here, irrelevant as a new exception will be thrown anyway
//        } catch (Exception e) {
//            log.error("Error instantiating the exception to be thrown for the interface '{}'",
//                    apiClass.getName(), e)
//        }
//        return defaultDecode(methodKey, response, restException) //fallback not presented here
//    }
//
//    private CommonServiceException getExceptionByReflection(RestException restException)
//            throws Exception {
//        CommonServiceException exceptionToBeThrown = null
//        ThrownServiceExceptionDetails exceptionDetails = exceptionsThrown.get(restException.getErrorCode())
//        if (exceptionDetails.hasMessageConstructor()) {
//            exceptionToBeThrown = exceptionDetails.getMessageConstructor().newInstance(restException.getMessage())
//        } else {
//            exceptionToBeThrown = exceptionDetails.getEmptyConstructor().newInstance()
//        }
//        return exceptionToBeThrown
//    }
}
