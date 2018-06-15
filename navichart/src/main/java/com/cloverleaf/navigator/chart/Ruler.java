package com.cloverleaf.navigator.chart;

import java.util.List;

public interface Ruler {

    /** Gets the spacing between ruler units */
    int getSpacing();

    /** Sets the spacing between ruler units */
    void setSpacing(int spacing);

    /** Gets the major ticks based on indent and size */
    List<?> getMajorTicks();

    /** Returns the underlying grid */
    <T> Grid<T> getGrid();
}
