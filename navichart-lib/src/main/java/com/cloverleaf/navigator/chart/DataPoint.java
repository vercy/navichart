package com.cloverleaf.navigator.chart;

import java.util.Objects;

public class DataPoint {
    private final String label;
    private final float value;

    public DataPoint(String label) {
        this.label = label;
        this.value = Float.NaN;
    }

    public DataPoint(String label, float value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public float getValue() {
        return value;
    }

    public boolean hasValue() {
        return Float.isNaN(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataPoint dataPoint = (DataPoint) o;
        return Float.compare(dataPoint.value, value) == 0 &&
                Objects.equals(label, dataPoint.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, value);
    }

    @Override
    public String toString() {
        return "{" +
                "'" + label + '\'' +
                ": " + value +
                '}';
    }
}
