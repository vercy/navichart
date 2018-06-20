package com.cloverleaf.navigator.navichartviewer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import com.cloverleaf.navigator.chart.DataPoint;

/** Swing component  */
public class JNaviChart extends JPanel {
    private String fileName;
    private DataPoint[] series = {
            new DataPoint("2018/Jun/11", 101.05f),
            new DataPoint("2018/Jun/12", 101.31f),
            new DataPoint("2018/Jun/13", 100.85f),
            new DataPoint("2018/Jun/14", 101.42f),
            new DataPoint("2018/Jun/15", 100.13f),
            new DataPoint("2018/Jun/18", 100.86f),
            new DataPoint("2018/Jun/19", 100.86f),
    };

    public String getFileName() {
        return fileName;
    }

    void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GraphicsConfiguration cfg = g2.getDeviceConfiguration();
        AffineTransform defaultTransform = cfg.getDefaultTransform();

        double scaleX = defaultTransform.getScaleX();
        double scaleY = defaultTransform.getScaleY();
        // 1 pixel wide stroke, assuming square pixels
        double strokeWidth = 1 / scaleX;
        double strokeHeight = 1 / scaleY;
        g2.setStroke(new BasicStroke((float)strokeWidth));

        double strokeHalfWidth = strokeWidth / 2;
        double strokeHalfHeight = strokeHeight / 2;

        String str = fileName == null || fileName.equalsIgnoreCase("") ? "No input file" : fileName;
        g2.drawString(str, 10, 100);
        g2.drawString("default scale: " + scaleX + ", " + scaleY, 10, 110);

        DataPointBounds bounds = findMinMax(series);
        Dimension size = getSize();
        int dataPointCount = series.length;
        g2.drawString("size: " + size.width + ", " + size.height, 10, 120);
        g2.drawString("bounds: " + bounds.min + ", " + bounds.max, 10, 130);
        g2.drawString("points: " + dataPointCount, 10, 140);

        for (int i = 2; i < 12; i++) {
            for (int j = 2; j < 12; j++) {
                g2.setColor(i % 2 == 0 && j % 2 == 0 ? Color.BLUE : Color.RED);
                g2.draw(new Rectangle.Double(i * strokeWidth + strokeHalfWidth, j * strokeWidth + strokeHalfHeight, strokeHalfWidth, strokeHalfHeight));
            }
        }

        g2.draw(new Line2D.Double(strokeHalfWidth,12 * strokeHeight + strokeHalfWidth,100 * strokeHalfWidth + strokeHalfWidth, 20 * strokeHeight + strokeHalfHeight));


        g2.setColor(Color.magenta);
        g2.draw(new Rectangle2D.Double(strokeHalfWidth, strokeHalfHeight, size.width-1 - strokeHalfWidth, size.height-1 - strokeHalfHeight));

        g2.setColor(Color.green);
        g2.draw(new Rectangle2D.Double(strokeHalfWidth * 3, strokeHalfHeight * 3, size.width - 1 - strokeHalfWidth * 4, size.height - 1 -strokeHalfHeight * 4));

        g2.setStroke(new BasicStroke((float)strokeWidth / 2));
        g2.setColor(Color.blue);
        double dataHeight = (bounds.max - bounds.min) * 1.2;
        double vStep = (double)size.width / (dataPointCount - 1);
        for(int i = 1; i < dataPointCount; i++) {
            // transform

            double x = vStep * (i-1);
            double y = (1 - ((series[i-1].getValue() - bounds.min + dataHeight / 12) / dataHeight)) * size.height;

            g2.draw(new Line2D.Double(
                    x,
                    y,
                    vStep * (i),
                    (1-((series[i].getValue() - bounds.min + dataHeight / 12) / dataHeight)) * size.height));
            g2.drawString(series[i-1].toString(), (float)x, (float)y);
        }

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
