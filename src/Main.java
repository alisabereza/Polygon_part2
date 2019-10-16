import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Enter the number of dots: ");
        Scanner in = new Scanner(System.in);
        Polygon polygon = new Polygon();
        polygon.setNumberOfDots(in.nextInt());
        polygon.setCoordinates(polygon.getNumberOfDots());
        polygon.setPolygon(polygon.getCoordinates());
        polygon.setConvexPolygon(polygon.getPolygon());
        polygon.setSegmentsOfPolygon(polygon.getConvexPolygon());
        polygon.setGun();
        polygon.setMostRemoteDots();
        in.nextLine();
        System.out.println("To build Max Quadrangle, N lines will be build from Gun to polygon, with the same equal angle between each two nearest.");
        System.out.println("Enter N: ");
        polygon.setSidesOfQuadrangle(in.nextInt());
        polygon.setTrapezium(polygon.getSidesOfQuadrangle());
    }
}

