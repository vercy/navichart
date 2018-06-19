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
        g2.drawString("default scale: " + defaultTransform.getScaleX() + ", " + defaultTransform.getScaleY(), 10, 110);
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                g2.setColor(i % 2 == 0 && j % 2 == 0 ? Color.BLUE : Color.RED);
                g2.fill(new Rectangle2D.Double(i * defaultTransform.getScaleX(), j * defaultTransform.getScaleY(), 1 * defaultTransform.getScaleX(), 1 * defaultTransform.getScaleY()));
            }
        }

        g2.draw(new Line2D.Double(0 * defaultTransform.getScaleX(),12*defaultTransform.getScaleY(),100*defaultTransform.getScaleX(), 20 * defaultTransform.getScaleY()));

        DataPointBounds bounds = findMinMax(series);
        Dimension size = getSize();
        g2.drawString("size: " + size.width + ", " + size.height, 10, 120);
        g2.drawString("bounds: " + bounds.min + ", " + bounds.max, 10, 130);
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
