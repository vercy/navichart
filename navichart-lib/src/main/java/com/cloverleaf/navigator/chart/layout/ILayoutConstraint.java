package com.cloverleaf.navigator.chart.layout;

public interface ILayoutConstraint {

    class Measure implements ILayoutConstraint {
        @Override
        public String toString() {
            return "MEASURE";
        }
    }

    class Reference implements ILayoutConstraint {
        private final int gridX;
        private final int gridY;

        public Reference(int gridX, int gridY) {
            this.gridX = gridX;
            this.gridY = gridY;
        }

        public int getGridX() {
            return gridX;
        }

        public int getGridY() {
            return gridY;
        }

        @Override
        public String toString() {
            return "REFERENCE(" + gridX + ", " + gridY + ')';
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
