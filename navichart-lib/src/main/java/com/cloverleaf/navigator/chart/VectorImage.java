package com.cloverleaf.navigator.chart;

public class VectorImage {

    private final Shape[] shapes;

    public VectorImage(Shape[] shapes) {
        this.shapes = shapes;
    }

    public Shape[] getShapes() {
        return shapes;
    }

    /** Common super interface for vector shapes */
    public interface Shape { }

    /** Line vector primitve */
    public static class Line implements Shape {
        final float x1;
        final float y1;
        final float x2;
        final float y2;

        public Line(float x1, float y1, float x2, float y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
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
        public String toString() {
            return "Line{(" + x1 + ", " + y1 + ") -> (" + x2 + ", " + y2 + ")}";
        }
    }

    /** Text vector primitve */
    public static class Text implements Shape {
        final float x;
        final float y;
        final String text;

        public Text(float x, float y, String text) {
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
        public String toString() {
            return "Text{(" + x + ", " + y + "), '" + text + "'}";
        }
    }
}
