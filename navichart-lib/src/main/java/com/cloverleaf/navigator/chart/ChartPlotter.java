package com.cloverleaf.navigator.chart;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChartPlotter {

    private int width;
    private int height;

    /** Sets the view port size and returns true if the operation was an update */
    public boolean setViewPortSize(int width, int height) {
        boolean update = this.width != width || this.height != height;
        this.width = width;
        this.height = height;
        return update;
    }

    public boolean isEmptyViewPort() {
        return width <=0 || height <= 0;
    }

    public VectorImage toImage(DataPoint[] data) {
        DataValueBounds bounds = findMinMax(data);
        float dataHeight = (bounds.max - bounds.min) * 1.2f;

        DataPoint[] vRulerTicks = getVerticalTicks(bounds);

        // 5% for the right ruler
        VectorImage.Style rulerTick = new VectorImage.Style(0.5f, Color.black.getRGB());
        List<VectorImage.Shape> img = new ArrayList<>();
        for(DataPoint tick : vRulerTicks) {
            // transform

            float x1 = 0.95f;
            float y1 = 1 - ((tick.getValue() - bounds.min + dataHeight / 12) / dataHeight);
            float x2 = 0.96f;

            img.add(new VectorImage.Line(x1 * width, y1 * height, x2 * width, y1 * height, rulerTick));
            img.add(new VectorImage.Text((x2 + 0.005f) * width, y1 * height, tick.getLabel()));
        }

        // 95%
        float vStep = 1.0f / (data.length - 1) * 0.95f;
        VectorImage.Style sparkLine = new VectorImage.Style(0.75f, Color.blue.getRGB());
        for(int i = 1; i < data.length; i++) {
            // transform

            float x1 = vStep * (i-1);
            float y1 = 1 - ((data[i-1].getValue() - bounds.min + dataHeight / 12) / dataHeight);
            float x2 = vStep * i;
            float y2 = 1 - ((data[i].getValue() - bounds.min + dataHeight / 12) / dataHeight);

            img.add(new VectorImage.Line(x1 * width, y1 * height, x2 * width, y2 * height, sparkLine));
//            img.add(new VectorImage.Text(x1, y1, data[i-1].toString()));
        }

        return new VectorImage(width, height, img.toArray(new VectorImage.Shape[0]));
    }

    private DataPoint[] getVerticalTicks(DataValueBounds bounds) {
        float dataHeight = (bounds.max - bounds.min) * 1.2f;
//        System.out.println(Math.log10(1) + " - " + Math.log10(5) + " - " + Math.log10(10) + " - " + Math.log10(50));
//        System.out.println(Math.log10(bounds.max) - Math.log10(bounds.min));
        DataPoint[] ticks = new DataPoint[5];
        for(int i = 0; i < ticks.length; i++) {
            float value = bounds.min + i * (dataHeight / ticks.length);
            ticks[i] = new DataPoint (String.format("%.2f",value), value);
        }

        return ticks;
    }

    static final class DataValueBounds {
        final float min, max;

        DataValueBounds(float min, float max) {
            this.min = min;
            this.max = max;
        }
    }

    private DataValueBounds findMinMax(DataPoint[] s) {
        float min = Float.MAX_VALUE, max = Float.MIN_VALUE;
        for(DataPoint d : s) {
            if(d == null || d.isEmpty())
                continue;

            if(min > d.getValue())
                min = d.getValue();
            if(max < d.getValue())
                max = d.getValue();
        }

        return new DataValueBounds(min, max);
    }
}
