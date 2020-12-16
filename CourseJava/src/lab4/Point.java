package lab4;

public class Point {
    public static double precision = 3.0;
    private double  x;
    private double  y;

    public Point() {
        this(0, 0);
    }

    public Point(double  x, double  y) {
        this.x = x;
        this.y = y;
    }

    public double  getX() {
        return Math.round(x * Math.pow(10.0, precision)) / Math.pow(10.0, precision);
    }

    public void setX(double  x) {
        this.x = x;
    }

    public double  getY() {
        return Math.round(y * Math.pow(10.0, precision)) / Math.pow(10.0, precision);
    }

    public void setY(double  y) {
        this.y = y;
    }
}
