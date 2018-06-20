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
        String str = fileName == null || fileName.equalsIgnoreCase("") ? "No input file" : fileName;
        GraphicsConfiguration cfg = g2.getDeviceConfiguration();
        AffineTransform defaultTransform = cfg.getDefaultTransform();

        g2.drawString(str, 10, 100);
        double scaleX = defaultTransform.getScaleX();
        double scaleY = defaultTransform.getScaleY();

        g2.drawString("default scale: " + scaleX + ", " + scaleY, 10, 110);
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                g2.setColor(i % 2 == 0 && j % 2 == 0 ? Color.BLUE : Color.RED);
                g2.fill(new Rectangle2D.Double(i * scaleX, j * scaleY, 1 * scaleX, 1 * scaleY));
            }
        }

        g2.draw(new Line2D.Double(0 * scaleX,12* scaleY,100* scaleX, 20 * scaleY));

        DataPointBounds bounds = findMinMax(series);
        Dimension size = getSize();
        int dataPointCount = series.length;
        g2.drawString("size: " + size.width + ", " + size.height, 10, 120);
        g2.drawString("bounds: " + bounds.min + ", " + bounds.max, 10, 130);
        g2.drawString("points: " + dataPointCount, 10, 140);

        // taking scale into account with the stroke width
        double strokeWidth = 1 / scaleX;
        double strokeHeight = 1 / scaleY;
        g2.setStroke(new BasicStroke((float)strokeWidth));

        double strokeHalfWidth = strokeWidth / 2;
        double strokeHalfHeight = strokeHeight / 2;
        g2.setColor(Color.magenta);
        g2.draw(new Rectangle2D.Double(strokeHalfWidth, strokeHalfHeight, size.width-1 - strokeHalfWidth, size.height-1 - strokeHalfHeight));

        g2.setColor(Color.green);
        g2.draw(new Rectangle2D.Double(strokeHalfWidth * 3, strokeHalfHeight * 3, size.width - 1 - strokeHalfWidth * 4, size.height - 1 -strokeHalfHeight * 4));

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
