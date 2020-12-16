package lab4;

public class Main {
    public static void main(String[] args) {
        //Triangle triangle = new Triangle(new Point(0, 0), new Point(3, 4), new Point(6, 1));
        Triangle triangle = new Triangle();
        System.out.println("Square = " + triangle.getSquare());
        System.out.println("Perimeter = " + triangle.getPerimeter());
        System.out.println("A (" + triangle.getA().getX() + ", " + triangle.getA().getY() + ")");
        System.out.println("B (" + triangle.getB().getX() + ", " + triangle.getB().getY() + ")");
        System.out.println("C (" + triangle.getC().getX() + ", " + triangle.getC().getY() + ")");
        System.out.println("Intersection's point (" + triangle.getMedianIntersectionPoint().getX() + ", " + triangle.getMedianIntersectionPoint().getY() + ")");
        System.out.println("Intersection's point (" + triangle.getMedianIntersectionPointCramer().getX() + ", " + triangle.getMedianIntersectionPointCramer().getY() + ")");
    }
}