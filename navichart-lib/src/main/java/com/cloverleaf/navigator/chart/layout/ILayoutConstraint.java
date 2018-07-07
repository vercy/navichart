package com.cloverleaf.navigator.chart.layout;

public interface ILayoutConstraint {

    class Measure implements ILayoutConstraint {
        @Override
        public String toString() {
            return "MEASURE";
        }
    }

    class Weight implements ILayoutConstraint {
        private final float weight;

        public Weight(float weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "WEIGHT(" + weight + '}';
        }
    }
}
