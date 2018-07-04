package com.cloverleaf.navigator.chart;

import com.cloverleaf.navigator.chart.ruler.Base10Ruler;

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
        Algorithms.FloatRange bounds = Algorithms.findMinMax(data, DataPoint::getValue);
        float dataHeight = (bounds.max - bounds.min) * 1.2f;

        Base10Ruler ruler = new Base10Ruler();
        DataPoint[] vRulerTicks = ruler.getVerticalTicks(bounds.min, bounds.max);

        // 5% for the right ruler
        VectorImage.Style rulerTick = new VectorImage.Style(0.5f, Color.black.getRGB());
        VectorImage.Style rulerGuide = new VectorImage.Style(0.1f, Color.black.getRGB());
        List<VectorImage.Shape> img = new ArrayList<>();
        int vRulerWidth = 50;
        int hRulerHeight = 25;
        img.add(new VectorImage.Line(width - vRulerWidth, 0, width - vRulerWidth, height - hRulerHeight, rulerTick)); // TODO move this into segment renderer
        for(DataPoint tick : vRulerTicks) {
            // transform

            float x1 = width - vRulerWidth;
            float y1 = (1 - ((tick.getValue() - bounds.min + dataHeight / 12) / dataHeight)) * (height - hRulerHeight) - rulerTick.getStrokeWidth() / 2;
            float x2 = width - vRulerWidth;

            img.add(new VectorImage.Line(x1, y1, x2, y1, rulerTick));
            img.add(new VectorImage.Line(0, y1, x2, y1, rulerGuide));
            img.add(new VectorImage.Text(x2 + 10, y1 + 3.5f, tick.getLabel()));
        }


        img.add(new VectorImage.Line(0, height - hRulerHeight, width - vRulerWidth, height - hRulerHeight, rulerTick));
//        for(DataPoint tick : vRulerTicks) {
//            // transform
//
//            float x1 = width - vRulerWidth;
//            float y1 = (1 - ((tick.getValue() - bounds.min + dataHeight / 12) / dataHeight)) * height - rulerTick.getStrokeWidth() / 2;
//            float x2 = width - vRulerWidth;
//
//            img.add(new VectorImage.Line(x1, y1, x2, y1, rulerTick));
//            img.add(new VectorImage.Line(0, y1, x2, y1, rulerGuide));
//            img.add(new VectorImage.Text(x2 + 10, y1 + 3.5f, tick.getLabel()));
//        }

        // 95%
        float vStep = 1.0f / (data.length - 1) * 0.95f;
        VectorImage.Style sparkLine = new VectorImage.Style(2f, Color.black.getRGB());
        for(int i = 1; i < data.length; i++) {
            // transform

            float x1 = vStep * (i-1);
            float y1 = 1 - ((data[i-1].getValue() - bounds.min + dataHeight / 12) / dataHeight);
            float x2 = vStep * i;
            float y2 = 1 - ((data[i].getValue() - bounds.min + dataHeight / 12) / dataHeight);

            img.add(new VectorImage.Line(x1 * (width - vRulerWidth), y1 * (height - hRulerHeight), x2 * (width - vRulerWidth), y2 * (height - hRulerHeight), sparkLine));
        }

        return new VectorImage(width, height, img.toArray(new VectorImage.Shape[0]));
    }


}
