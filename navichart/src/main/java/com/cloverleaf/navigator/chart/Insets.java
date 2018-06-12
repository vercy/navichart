package com.cloverleaf.navigator.chart;


import java.util.Objects;

public class Insets {
    private int left;
    private int top;
    private int bottom;
    private int right;

    /** Create an empty Insets class */
    public Insets() {
    }

    /** Copy constructor */
    public Insets(Insets i)
    {
        this.left = i.left;
        this.top = i.top;
        this.bottom = i.bottom;
        this.right = i.right;
    }

    /** New insets form components */
    public Insets(int left, int top, int bottom, int right) {
        this.left = left;
        this.top = top;
        this.bottom = bottom;
        this.right = right;
    }

    int getLeft() {
        return left;
    }

    void setLeft(int left) {
        this.left = left;
    }

    int getTop() {
        return top;
    }

    void setTop(int top) {
        this.top = top;
    }

    int getBottom() {
        return bottom;
    }

    void setBottom(int bottom) {
        this.bottom = bottom;
    }

    int getRight() {
        return right;
    }

    void setRight(int right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Insets insets = (Insets) o;
        return left == insets.left &&
                top == insets.top &&
                bottom == insets.bottom &&
                right == insets.right;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, top, bottom, right);
    }

    @Override
    public String toString() {
        return "{" +
                "left=" + left +
                ", top=" + top +
                ", bottom=" + bottom +
                ", right=" + right +
                '}';
    }
}
