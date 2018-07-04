package com.cloverleaf.navigator.chart;

import java.util.Arrays;

class Algorithms {

    static <T> FloatRange findMinMax(T[] list, ToFloatFunction<T> f) {
        return list != null ? findMinMax(Arrays.asList(list), f) : FloatRange.EMPTY_RANGE;
    }

    static <T> FloatRange findMinMax(Iterable<T> list, ToFloatFunction<T> f) {
        float min = Float.MAX_VALUE, max = Float.MIN_VALUE;
        boolean foundAtLeastOneRealNumber = false;
        for(T d : list) {
            if(d == null)
                continue;

            float value = f.applyAsFloat(d);
            if(Float.isNaN(value) || Float.isInfinite(value))
                continue;

            if(min > value)
                min = value;
            if(max < value)
                max = value;

            foundAtLeastOneRealNumber = true;
        }

        return foundAtLeastOneRealNumber ? new FloatRange(min, max) : FloatRange.EMPTY_RANGE;
    }

    static final class FloatRange {
        final float min, max;

        static final FloatRange EMPTY_RANGE = new FloatRange(Float.NaN, Float.NaN);

        FloatRange(float min, float max) {
            this.min = min;
            this.max = max;
        }

        public float getMin() {
            return min;
        }

        public float getMax() {
            return max;
        }

        @Override
        public String toString() {
            return "Range[" + min + ", " + max + ']';
        }
    }
}
