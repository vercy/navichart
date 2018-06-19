package com.cloverleaf.navigator.chart;

import java.util.ArrayList;
import java.util.List;

public class InterpolatorGrid implements Grid<Double> {
    private double minValue;
    private double maxValue;

    /** Creates a bounded linear interpolator with between min and max */
    public InterpolatorGrid(double min, double max) {
        minValue = min;
        maxValue = max;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public List<Double> getData() {
        List<Double> rv = new ArrayList<>();
        rv.add(minValue);
        rv.add(maxValue);
        return rv;
    }

    @Override
    public void setData(List<? extends Double> value) {
        if (value == null || value.size() < 2)
            return;

        Double min = value.get(0);
        Double max = value.get(1);
        if(min == null || max == null)
            return;

        if(min > max) {
            Double swap = max;
            max = min;
            min = swap;
        }

        this.minValue = min;
        this.maxValue = max;
    }

    @Override
    public Double getNearestObjectFor(double val) {
        return minValue + getNearestValueFor(val) * (maxValue - minValue);
    }

    @Override
    public double getNearestValueFor(Double o) {
        return getNearestValueFor((double)o);
    }

    public double getNearestValueFor(Object o) {
        double d = toDouble(o);
        return getNearestValueFor(d);
    }

    private double toDouble(Object o) {
        double d;
        if(o instanceof Long)
            d = ((Long)o).doubleValue();
        else if(o instanceof Integer)
            d = ((Integer)o).doubleValue();
        else if(o instanceof Float)
            d = ((Float)o).doubleValue();
        else
            d = (double)o;
        return d;
    }

    @Override
    public double getNearestValueFor(double val) {
        if (val < 0.0)
            return 0.0;
        if (val > 1.0)
            return 1.0;

        return val;
    }

    @Override
    public double getValueFor(Double d) {
        return (d - minValue) / (maxValue - minValue);
    }

    public double getValueFor(Object o) {
        return getValueFor(toDouble(o));
    }

    @Override
    public Double getObjectFor(double d) {
        return minValue + d * (maxValue - minValue);
    }
}
