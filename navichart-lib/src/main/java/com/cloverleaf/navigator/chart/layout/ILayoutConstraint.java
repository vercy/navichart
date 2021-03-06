package com.cloverleaf.navigator.chart.layout;

public interface ILayoutConstraint {

    class Measure implements ILayoutConstraint {
        @Override
        public String toString() {
            return "MEASURE";
        }
    }

    class Space implements ILayoutConstraint {
        private final float amount;

        public Space(float amount) {
            this.amount = amount;
        }

        public float getAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return "SPACE(" + amount + '}';
        }
    }

    class Weight implements ILayoutConstraint {
        private final float weight;

        public Weight(float weight) {
            this.weight = weight;
        }

        public float getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return "WEIGHT(" + weight + '}';
        }
    }
}
