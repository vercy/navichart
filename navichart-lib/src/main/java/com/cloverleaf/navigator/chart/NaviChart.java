package com.cloverleaf.navigator.chart;

/** The entry point of the chart component */
public class NaviChart {

    private ChartPlotter plotter = new ChartPlotter();
    private VectorImage chartImage = VectorImage.EMPTY;

    private DataPoint[] data;

    /** Updates the view port size to be used for rendering the chart */
    public void setViewPortSize(int width, int height) {
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
