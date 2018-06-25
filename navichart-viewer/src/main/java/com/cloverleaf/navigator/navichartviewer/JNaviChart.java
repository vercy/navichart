package com.cloverleaf.navigator.navichartviewer;

import com.cloverleaf.navigator.chart.DataPoint;
import com.cloverleaf.navigator.chart.NaviChart;
import com.cloverleaf.navigator.chart.VectorImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/** Swing component  */
public class JNaviChart extends JPanel {

    private static DataPoint[] series = Arrays.stream(DemoData.lines)
            .map(d -> new DataPoint(d.date.toString(), (float)d.adjClose))
            .collect(Collectors.toList())
            .toArray(new DataPoint[0]);

    private NaviChart naviChart = new NaviChart();

    public JNaviChart() {
        naviChart.setData(series);
        this.addComponentListener(new CListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        VectorImage img = naviChart.getChartImage();
        VectorImage.Style style = null;

        for(VectorImage.Shape s : img.getShapes()) {
            style = updateStyle(g2, style, s.getStyle());
            if(s instanceof VectorImage.Line) {
                VectorImage.Line line = (VectorImage.Line)s;
                g2.draw(new Line2D.Float(
                        line.getX1(),
                        line.getY1(),
                        line.getX2(),
                        line.getY2()));
            } else if(s instanceof VectorImage.Text) {
                VectorImage.Text text = (VectorImage.Text)s;
                g2.drawString(
                        text.getText(),
                        text.getX(),
                        text.getY());
            }
        }
    }

    private VectorImage.Style updateStyle(Graphics2D g, VectorImage.Style inherited, VectorImage.Style element) {
        if(element == null || Objects.equals(inherited, element))
            return inherited;

        float strokeWidth = inherited != null ? inherited.getStrokeWidth() : 1;
        if(strokeWidth != element.getStrokeWidth()) {
            strokeWidth = element.getStrokeWidth();
            g.setStroke(new BasicStroke(strokeWidth));

        }

        int strokeColor = inherited != null ? inherited.getStrokeColor() : Color.black.getRGB();
        if(strokeColor != element.getStrokeColor()) {
            strokeColor = element.getStrokeColor();
            g.setColor(new Color(strokeColor));
        }

        return new VectorImage.Style(strokeWidth, strokeColor);
    }

    class CListener implements ComponentListener {
        @Override
        public void componentResized(ComponentEvent e) {
            JComponent source = (JComponent)e.getSource();
            naviChart.setViewPortSize(source.getWidth(), source.getHeight());
        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {
            JComponent source = (JComponent)e.getSource();
            naviChart.setViewPortSize(source.getWidth(), source.getHeight());
        }

        @Override
        public void componentHidden(ComponentEvent e) {

        }
    }
}
