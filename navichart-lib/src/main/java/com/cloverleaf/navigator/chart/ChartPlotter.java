package com.cloverleaf.navigator.chart;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChartPlotter {

    public VectorImage toImage(DataPoint[] data) {
        DataPointBounds bounds = findMinMax(data);
        float dataHeight = (bounds.max - bounds.min) * 1.2f;
        float vStep = 1.0f / (data.length - 1);
        VectorImage.Style sparkLine = new VectorImage.Style(0.75f, Color.RED.getRGB());

        List<VectorImage.Shape> img = new ArrayList<>();
        for(int i = 1; i < data.length; i++) {
            // transform

            float x1 = vStep * (i-1);
            float y1 = 1 - ((data[i-1].getValue() - bounds.min + dataHeight / 12) / dataHeight);
            float x2 = vStep * i;
            float y2 = 1 - ((data[i].getValue() - bounds.min + dataHeight / 12) / dataHeight);

            img.add(new VectorImage.Line(x1, y1, x2, y2, sparkLine));
//            img.add(new VectorImage.Text(x1, y1, data[i-1].toString()));
        }

        return new VectorImage(img.toArray(new VectorImage.Shape[0]));
    }

    static final class DataPointBounds {
        final float min, max;

        DataPointBounds(float min, float max) {
            this.min = min;
            this.max = max;
        }
    }

    private DataPointBounds findMinMax(DataPoint[] s) {
        float min = Float.MAX_VALUE, max = Float.MIN_VALUE;
        for(DataPoint d : s) {
            if(d == null || d.isEmpty())
                continue;

            if(min > d.getValue())
                min = d.getValue();
            if(max < d.getValue())
                max = d.getValue();
        }

        return new DataPointBounds(min, max);
    }
}
