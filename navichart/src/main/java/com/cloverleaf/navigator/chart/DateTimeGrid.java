package com.cloverleaf.navigator.chart;

import java.util.List;

public class DateTimeGrid<T> implements Grid<T> {
    @Override
    public List<T> getData() {
        return null;
    }

    @Override
    public void setData(List<?> data) {

    }

    @Override
    public T getNearestObjectFor(double val) {
        return null;
    }

    @Override
    public double getNearestValueFor(double val) {
        return 0;
    }

    @Override
    public double getNearestValueFor(T o) {
        return 0;
    }

    @Override
    public double getValueFor(T o) {
        return 0;
    }

    @Override
    public T ObjectFor(double val) {
        return null;
    }
}
