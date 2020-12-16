package lab4;

//Описать класс, представляющий треугольник.
//Предусмотреть методы для создания объектов, вычисления площади,
//периметра и точки пересечения медиан. Описать свойства для получения состояния объекта.

public class Triangle {
    private Point a;
    private Point b;
    private Point c;

    public Triangle() {
        do {
            this.a = new Point(mathRandom(0, 20), mathRandom(0, 20));
            this.b = new Point(mathRandom(0, 20), mathRandom(0, 20));
            this.c = new Point(mathRandom(0, 20), mathRandom(0, 20));
        } while (!isTriangle());
    }

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    Point getMedianIntersectionPoint() {
        return new Point((a.getX() + b.getX() + c.getX()) / 3.0, (a.getY() + b.getY() + c.getY()) / 3.0);
    }

    public Point getMedianIntersectionPointCramer() {
        Point n = new Point((a.getX() + c.getX()) / 2.0, (a.getY() + c.getY()) / 2.0);
        Point l = new Point((a.getX() + b.getX()) / 2.0, (a.getY() + b.getY()) / 2.0);
        double c1 = (b.getX() * n.getY() - n.getX() * b.getY()), c2 = (c.getX() * l.getY() - l.getX() * c.getY()),
                c3 = (c.getX() - l.getX()), c4 = (l.getY() - c.getY()), c5 = (b.getX() - n.getX()), c6 = (n.getY() - b.getY()),
                cramer = c3 * c6 - c4 * c5, cramer1 = c1 * c3 - c2 * c5, cramer2 = c2 * c6 - c1 * c4;
        return new Point(cramer1 / cramer, cramer2 / cramer);
    }

    public double getSquare() {
        double polyP = getPerimeter() / 2.0;
        return Math.round((Math.sqrt(polyP * (polyP - dist(a, b)) * (polyP - dist(b, c)) * (polyP - dist(c, a)))) * Math.pow(10.0, Point.precision)) / Math.pow(10.0, Point.precision);
    }

    public double getPerimeter() {
        return Math.round((dist(a, b) + dist(b, c) + dist(c, a)) * Math.pow(10.0, Point.precision)) / Math.pow(10.0, Point.precision);
    }

    private boolean isTriangle() {
        double A = dist(a, b), B = dist(b, c), C = dist(c, a);
        return (A + B > C && B + C > A && C + A > B);
    }

    private double dist(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    private static double mathRandom(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public Point getB() {
        return b;
    }

    public void setB(Point b) {
        this.b = b;
    }

    public Point getC() {
        return c;
    }

    public void setC(Point c) {
        this.c = c;
    }
}
