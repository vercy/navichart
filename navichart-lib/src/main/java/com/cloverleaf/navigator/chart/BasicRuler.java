package com.cloverleaf.navigator.chart;

public class BasicRuler {

    /** Generate equidistant ticks for the passed data interval */
    DataPoint[] getVerticalTicks(float min, float max) {
        float dataHeight = (max - min) * 1.2f;
        float tickUnit = getTickUnit(dataHeight);
        float firstTick = (float)Math.ceil(min / tickUnit) * tickUnit;
        int maxTickDistance = (int)Math.floor((max - firstTick) / tickUnit);

        DataPoint[] ticks = new DataPoint[maxTickDistance + 1];
        for(int i = 0; i < ticks.length; i++) {
            float value = firstTick + i * tickUnit;
            ticks[i] = new DataPoint (formatTickText(value, tickUnit), value);
        }

        return ticks;
    }

    /** Picks the most coarse tick unit that can be related to the data interval */
    private float getTickUnit(float dataInterval) {
        double ticks10 = Math.pow(10, Math.floor(Math.log10(dataInterval)));
        if(dataInterval / ticks10 > 3)
            return (float) ticks10;

        double ticks5 = ticks10 / 2;
        if(dataInterval / ticks5 > 3)
            return (float)ticks5;

        return (float)ticks10 / 5;
    }

    /** Format ruler tick text based on tick unit size */
    String formatTickText(float value, float unit) {
        if(unit >= 1f)
            return String.format("%.0f", value);
        else if(unit >= 0.1f)
            return String.format("%.1f", value);
        else if(unit >= 0.01f)
            return String.format("%.2f", value);
        else if(unit >= 0.001f)
            return String.format("%.3f", value);
        else if(unit >= 0.0001f)
            return String.format("%.4f", value);
        else if(unit >= 0.00001f)
            return String.format("%.4f", value);
        else
            return String.format("%.6f", value);
    }

}
