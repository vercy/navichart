package com.cloverleaf.navigator.chart;

import java.util.Objects;

public class VectorImage {

    private final Shape[] shapes;

    VectorImage(Shape[] shapes) {
        this.shapes = shapes;
    }

    public Shape[] getShapes() {
        return shapes;
    }

    /** Common super interface for vector shapes */
    public interface Shape {
        Style getStyle();
    }

    /** Line vector primitve */
    public static class Line implements Shape {
        final float x1;
        final float y1;
        final float x2;
        final float y2;
        final Style style;

        Line(float x1, float y1, float x2, float y2, Style style) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.style = style;
        }

        public float getX1() {
            return x1;
        }

        public float getY1() {
            return y1;
        }

        public float getX2() {
            return x2;
        }

        public float getY2() {
            return y2;
        }

        @Override
        public Style getStyle() {
            return style;
        }

        @Override
        public String toString() {
            return "Line{(" + x1 + ", " + y1 + ") -> (" + x2 + ", " + y2 + ")}";
        }
    }

    /** Text vector primitve */
    public static class Text implements Shape {
        final float x;
        final float y;
        final String text;

        Text(float x, float y, String text) {
            this.x = x;
            this.y = y;
            this.text = text;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public String getText() {
            return text;
        }

        @Override
        public Style getStyle() {
            return null; // TODO
        }

        @Override
        public String toString() {
            return "Text{(" + x + ", " + y + "), '" + text + "'}";
        }
    }

    public static class Style {
        private final float strokeWidth;
        private final int strokeColor;

        public Style(float strokeWidth, int strokeColor) {
            this.strokeWidth = strokeWidth;
            this.strokeColor = strokeColor;
        }

        public float getStrokeWidth() {
            return strokeWidth;
        }

        public int getStrokeColor() {
            return strokeColor;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Style style = (Style) o;
            return Float.compare(style.strokeWidth, strokeWidth) == 0 &&
                    strokeColor == style.strokeColor;
        }

        @Override
        public int hashCode() {
            return Objects.hash(strokeWidth, strokeColor);
        }

        @Override
        public String toString() {
            return "Style{" +
                    "strokeWidth=" + strokeWidth +
                    ", strokeColor=" + strokeColor +
                    '}';
        }
    }
}
