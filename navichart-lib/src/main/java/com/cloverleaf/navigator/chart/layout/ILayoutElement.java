package com.cloverleaf.navigator.chart.layout;

import java.awt.geom.Rectangle2D;

/** Interface for layout components */
public interface ILayoutElement {

    /** returns the ideal width of the element */
    float measureWidth();

    /** returns the ideal height of the element */
    float measureHeight();

    /** Updates the size of the component as a amounts of the layout calculation */
    @Deprecated
    void setSize(float width, float height);

    /** Updates the counds of the component as a amounts of the layout calculation */
    void setBounds(Rectangle2D boundingRect);
}
