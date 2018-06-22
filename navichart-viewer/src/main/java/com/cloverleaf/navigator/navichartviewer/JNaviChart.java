package com.cloverleaf.navigator.navichartviewer;

import com.cloverleaf.navigator.chart.ChartPlotter;
import com.cloverleaf.navigator.chart.DataPoint;
import com.cloverleaf.navigator.chart.VectorImage;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.stream.Collectors;

/** Swing component  */
public class JNaviChart extends JPanel {

    private static DataPoint[] series = Arrays.stream(DemoData.lines)
            .map(d -> new DataPoint(d.date.toString(), (float)d.adjClose))
            .collect(Collectors.toList())
            .toArray(new DataPoint[0]);

    private ChartPlotter plotter = new ChartPlotter();

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Dimension size = getSize();
        VectorImage img = plotter.toImage(series);

        for(VectorImage.Shape s : img.getShapes()) {
            if(s instanceof VectorImage.Line) {
                VectorImage.Line line = (VectorImage.Line)s;
                g2.draw(new Line2D.Float(
                        line.getX1() * size.width,
                        line.getY1() * size.height,
                        line.getX2() * size.width,
                        line.getY2() * size.height));
            } else if(s instanceof VectorImage.Text) {
                VectorImage.Text text = (VectorImage.Text)s;
                g2.drawString(
                        text.getText(),
                        text.getX() * size.width,
                        text.getY() * size.height);
            }
        }
    }


}
