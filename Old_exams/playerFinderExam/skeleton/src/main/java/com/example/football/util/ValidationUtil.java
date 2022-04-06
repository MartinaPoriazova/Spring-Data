package com.example.football.util;

public interface ValidationUtil {

    <E> boolean isValid(E entity);

//    <T>Set<ConstraintViolation<T>> violation(T entity);
}
