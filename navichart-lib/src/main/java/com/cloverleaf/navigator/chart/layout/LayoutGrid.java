package com.cloverleaf.navigator.chart.layout;

import java.util.*;

public class LayoutGrid implements ILayout {

    private static final ILayoutConstraint[] EMPTY = {};

    private ILayoutConstraint[] rows = EMPTY;
    private ILayoutConstraint[] columns = EMPTY;
    private final Map<Integer, ILayoutElement> elements = new HashMap<>();

    public void setRows(ILayoutConstraint... c) {
        rows = c != null ? c : EMPTY;
    }

    public void setColumns(ILayoutConstraint... c) {
        columns = c != null ? c : EMPTY;
    }

    public void setElement(int gx, int gy, ILayoutElement e) {
        elements.put(GridPoint.encode(gx, gy), e);
    }

    @Override
    public void layout(int width, int height) {
        float[] vertical = doLayout(width, rows);
        float[] horizontal = doLayout(height, columns);

        elements.forEach((idx, e) -> {
            if(e == null)
                return;

            int rawIndex = idx;
            e.setSize(
                    horizontal[GridPoint.getX(rawIndex)],
                    vertical[GridPoint.getY(rawIndex)]);
        });
    }

    float[] doLayout(float total, ILayoutConstraint[] constraint) {
        int l = constraint != null ? constraint.length : 0;
        // 1) evaluate measureables, subtract from width / height
        // 2) collect weights and distribute
        return new float[l];
    }

    @Override
    public String toString() {
        return "LayoutGrid(" + rows.length + " x " + columns.length+ ")";
    }

    static class GridPoint {

        static int encode(int gx, int gy) {
            if(gx < 0 || Short.MAX_VALUE < gx)
                throw new IllegalArgumentException("Invalid GridPoint.x value " + gx + ". Valid range: 0..32767");
            if(gy < 0 || Short.MAX_VALUE < gy)
                throw new IllegalArgumentException("Invalid GridPoint.y value " + gy + ". Valid range: 0..32767");

            return (gx << 16) | (gy & 0xffff);
        }

        static int getX(int encoded) {
            return (encoded >> 16) & 0xffff;
        }

        static int getY(int encoded) {
            return encoded & 0xffff;
        }

        public static  String toString(int encoded)
        {
            return "(" + getX(encoded) + ", " + getY(encoded) + ")";
        }
    }
}
