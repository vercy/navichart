package com.cloverleaf.navigator.chart;

/** ToFloatFunction is not present in the standard library*/
@FunctionalInterface
public interface ToFloatFunction<T> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    float applyAsFloat(T value);

}
