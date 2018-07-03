package com.cloverleaf.navigator.chart.layout;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.ToIntFunction;

public class LayoutGrid implements ILayout {

    private final int gridWidth;
    private final int gridHeight;
    private final LayoutGridPoint[] gridPoints;

    private LayoutGrid(int gWidth, int gHeight, LayoutGridPoint[] gridPoints) {
        this.gridWidth = gWidth;
        this.gridHeight = gHeight;
        this.gridPoints = gridPoints;
    }

    @Override
    public void layout(int with, int height) {
        // TODO
    }

    @Override
    public String toString() {
        return "LayoutGrid{(" + gridWidth + " x " + gridHeight + "), gridPoints=" + Arrays.toString(gridPoints) + '}';
    }

    static class LayoutGridPoint {
        final ILayoutElement element;
        final int gx;
        final int gy;
        final ILayoutConstraint cx;
        final ILayoutConstraint cy;

        LayoutGridPoint(ILayoutElement element, int gx, int gy, ILayoutConstraint cx, ILayoutConstraint cy) {
            this.element = element;
            this.gx = gx;
            this.gy = gy;
            this.cx = cx;
            this.cy = cy;
        }

        @Override
        public String toString() {
            return "LayoutGridPoint{(" + gx + ", " + gy + ") => (" + cx + ", " + cy + ")}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            LayoutGridPoint that = (LayoutGridPoint) o;
            return gx == that.gx && gy == that.gy;
        }

        @Override
        public int hashCode() {
            return Objects.hash(gx, gy);
        }
    }

    public static class Builder {
        final Set<LayoutGridPoint> gridPoints = new LinkedHashSet<>();

        public Builder add(ILayoutElement e, int gx, int gy, ILayoutConstraint cx, ILayoutConstraint cy) {
            Objects.requireNonNull(e, "LayoutElement cannot be null");
            Objects.requireNonNull(cx, "X axis constraint cannot be null");
            Objects.requireNonNull(cy, "Y axis constraint cannot be null");

            if(gx < 0)
                throw new IllegalArgumentException("X grid coordinate must be >0. value: " + gx);
            if(gy < 0)
                throw new IllegalArgumentException("Y grid coordinate must be >0. value: " + gy);

            gridPoints.add(new LayoutGridPoint(e, gx, gy, cx, cy));

            return this;
        }

        public LayoutGrid build() {
            int gWidth = findMax(gridPoints, p -> p.gx) + 1;
            int gHeight = findMax(gridPoints, p -> p.gy) + 1;
            LayoutGridPoint[] gridPointsArray = gridPoints.toArray(new LayoutGridPoint[0]);

            // TODO

            return new LayoutGrid(gWidth, gHeight, gridPointsArray);
        }

        int findMax(Iterable<LayoutGridPoint> pts, ToIntFunction<LayoutGridPoint> metric) {
            int max = Integer.MIN_VALUE;
            for(LayoutGridPoint p : pts) {
                int current = metric.applyAsInt(p);
                if (current > max)
                    max = current;
            }
            return max;
        }
    }
}
