package com.cloverleaf.navigator.chart;

import com.cloverleaf.navigator.chart.layout.ILayoutConstraint;
import com.cloverleaf.navigator.chart.layout.LayoutGrid;

/** The entry point of the chart component */
public class NaviChart {

    private ChartPlotter plotter = new ChartPlotter();
    private LayoutGrid layout = new LayoutGrid();
    private VectorImage chartImage = VectorImage.EMPTY;

    private DataPoint[] data;

    public NaviChart() {
        layout.setColumnConstraints(
                new ILayoutConstraint.Weight(1),
                new ILayoutConstraint.Measure());
        layout.setRowConstraints(
                new ILayoutConstraint.Weight(1),
                new ILayoutConstraint.Measure());
        layout.setElement(0,0, null);
        layout.setElement(1,0, null);
        layout.setElement(0,1, null);
    }

    /** Updates the view port size to be used for rendering the chart */
    public void setViewPortSize(int width, int height) {
        layout.layout(width, height);
        boolean update = plotter.setViewPortSize(width, height);
        if(update)
            plotChart();
    }

    /** Returns the latest image */
    public VectorImage getChartImage() {
        return chartImage;
    }

    /** Sets the data points to render */
    public void setData(DataPoint[] data) {
        this.data = data;
        plotChart();
    }


    private void plotChart() {
        if(data == null || plotter.isEmptyViewPort()) {
            chartImage = VectorImage.EMPTY;
        } else {
            chartImage = plotter.toImage(data);
        }
    }

}
