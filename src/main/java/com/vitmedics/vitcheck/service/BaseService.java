package com.vitmedics.vitcheck.service;

import com.vitmedics.vitcheck.exception.EntityType;
import com.vitmedics.vitcheck.exception.ExceptionType;
import com.vitmedics.vitcheck.exception.VitcheckException;

public class BaseService {

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    protected RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return VitcheckException.throwException(entityType, exceptionType, args);
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    protected RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType, Integer id, String... args) {
        return VitcheckException.throwExceptionWithId(entityType, exceptionType, id, args);
    }
}
