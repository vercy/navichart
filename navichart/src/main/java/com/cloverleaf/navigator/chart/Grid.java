package com.cloverleaf.navigator.chart;

import java.util.List;

public interface Grid<T> {
    List<T> getData();
    void setData(List<?> data);

    /** Gets closest Object to the passed value */
    T	getNearestObjectFor(double val);

    /** Gets the actual value closest to the passed approximate value */
    double	getNearestValueFor(double val);

    /** Gets the value of the object closest to the passed object */
    double	getNearestValueFor(T o);

    /** Gets the value af the passed Object */
    double	getValueFor(T o);

    /** Gets the object for the passed value. */
    T	ObjectFor(double val);
}
