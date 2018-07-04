package com.cloverleaf.navigator.chart.layout;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class LayoutGrid implements ILayout {

    private final int gridWidth;
    private final int gridHeight;
    private final GridPoint[] gridPoints;

    private LayoutGrid(int gWidth, int gHeight, GridPoint[] gridPoints) {
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

    static class GridPoint {
        final int gx;
        final int gy;

        public GridPoint(int gx, int gy) {
            this.gx = gx;
            this.gy = gy;
        }

        @Override
        public String toString() {
            return "(" + gx + ", " + gy + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            GridPoint that = (GridPoint) o;
            return gx == that.gx && gy == that.gy;
        }

        @Override
        public int hashCode() {
            return Objects.hash(gx, gy);
        }
    }

    static class GridElement {
        final ILayoutElement element;
        final ILayoutConstraint cx;
        final ILayoutConstraint cy;

        public GridElement(ILayoutElement element, ILayoutConstraint cx, ILayoutConstraint cy) {
            this.element = element;
            this.cx = cx;
            this.cy = cy;
        }

        @Override
        public String toString() {
            return "(" + cx + ", " + cy + ")}";
        }
    }

    public static class Builder {
        final Map<GridPoint, GridElement> gridPoints = new LinkedHashMap<>();

        public Builder add(ILayoutElement e, int gx, int gy, ILayoutConstraint cx, ILayoutConstraint cy) {
            Objects.requireNonNull(e, "LayoutElement cannot be null");
            Objects.requireNonNull(cx, "X axis constraint cannot be null");
            Objects.requireNonNull(cy, "Y axis constraint cannot be null");

            if(gx < 0)
                throw new IllegalArgumentException("X grid coordinate must be >0. value: " + gx);
            if(gy < 0)
                throw new IllegalArgumentException("Y grid coordinate must be >0. value: " + gy);

            gridPoints.put(new GridPoint(gx, gy), new GridElement(e, cx, cy));

            return this;
        }

        public LayoutGrid build() {

            // TODO

            return null;
        }
    }
}
