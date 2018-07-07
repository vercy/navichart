package com.cloverleaf.navigator.chart.layout;

/** Interface for layout components */
public interface ILayoutElement {

    /** returns the ideal width of the element */
    float measureWidth();

    /** returns the ideal height of the element */
    float measureHeight();

    /** Updates the size of the component as a result of the layout calculation */
    void setSize(float width, float height);
}
