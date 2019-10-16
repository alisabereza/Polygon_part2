import java.util.Scanner;

public class Polygon {
    private int numberOfDots;
    private double[][] coordinates;
    private Dot[] polygon;
    private Dot[] convexPolygon;
    private LineSegment[] segmentsOfPolygon;
    private int[] indexOfSegmentOnLineWithGun;
    private Dot gun, mostRemoteDot1, mostRemoteDot2;
    private double maxAngle;
    private LineSegment[] sidesOfQuadrangle;
    private Dot[] trapezium;

    void setIndexOfSegmentOnLineWithGun() {
        int[] indexOfSegmentOnLineWithGun = new int[2];
        indexOfSegmentOnLineWithGun[0] = -1;
        indexOfSegmentOnLineWithGun[1] = -1;
        this.indexOfSegmentOnLineWithGun = indexOfSegmentOnLineWithGun;
    }

    void setNumberOfDots(int number) {
        numberOfDots = number;
        System.out.println("Number of dots set: " + numberOfDots);
    }

    int getNumberOfDots() {
        return numberOfDots;
    }

    void setCoordinates(int numberOfDots) {
        double[][] coordinates = new double[numberOfDots][2];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < numberOfDots; i++) {
            System.out.println("Enter coordinates for dot " + (i + 1) + ":");
            for (int j = 0; j < 2; j++) {
                System.out.print((j + 1) + " :");
                coordinates[i][j] = sc.nextDouble();
                sc.nextLine();
            }
            System.out.println("Dot " + (i + 1) + " is: (" + coordinates[i][0] + "," + coordinates[i][1] + ")");
        }
        this.coordinates = coordinates;
    }

    double[][] getCoordinates() {
        return coordinates;
    }

    void setPolygon(double[][] coordinates) {
        Dot[] polygon = new Dot[numberOfDots];
        double value = 0;
        for (int i = 0; i < numberOfDots; i++) {
            polygon[i] = new Dot(coordinates[i][0], coordinates[i][1]);
        }
        for (int i = 0; i < numberOfDots; i++) {
            if (i == 0) {
                value = Dot.ProductOfTwoVectors(polygon[numberOfDots - 1], polygon[0], polygon[1]);
                if (value > 0) {
                    polygon[i].setPositive(true);
                    System.out.println("Polygon: dot added: " + i + ": x: " + polygon[i].getX() + ", y: " + polygon[i].getY() + ", is Positive: " + polygon[i].isPositive());
                } else {
                    polygon[i].setPositive(false);
                    System.out.println("Polygon: dot added: " + i + ": x: " + polygon[i].getX() + ", y: " + polygon[i].getY() + ", is Positive: " + polygon[i].isPositive());
                }
            } else if (i == numberOfDots - 1) {
                value = Dot.ProductOfTwoVectors(polygon[numberOfDots - 2], polygon[numberOfDots - 1], polygon[0]);
                if (value > 0) {
                    polygon[i].setPositive(true);
                    System.out.println("Polygon: dot added: " + i + ": x: " + polygon[i].getX() + ", y: " + polygon[i].getY() + ", is Positive: " + polygon[i].isPositive());
                } else {
                    polygon[i].setPositive(false);
                    System.out.println("Polygon: dot added: " + i + ": x: " + polygon[i].getX() + ", y: " + polygon[i].getY() + ", is Positive: " + polygon[i].isPositive());
                }
            } else {
                value = Dot.ProductOfTwoVectors(polygon[i - 1], polygon[i], polygon[i + 1]);
                if (value > 0) {
                    polygon[i].setPositive(true);
                    System.out.println("Polygon: dot added: " + i + ": x: " + polygon[i].getX() + ", y: " + polygon[i].getY() + ", is Positive: " + polygon[i].isPositive());
                } else {
                    polygon[i].setPositive(false);
                    System.out.println("Polygon: dot added: " + i + ": x: " + polygon[i].getX() + ", y: " + polygon[i].getY() + ", is Positive: " + polygon[i].isPositive());
                }
            }
        }
        this.polygon = polygon;
    }

    Dot[] getPolygon() {
        return polygon;
    }

    void setConvexPolygon(Dot[] polygon) {
        int numberOfPositive = 0;
        int numberOfNegative = 0;
        for (int i = 0; i < polygon.length; i++) {
            if (polygon[i].isPositive() == true) {
                numberOfPositive += 1;
            } else if (polygon[i].isPositive() == false) {
                numberOfNegative += 1;
            }
        }
        if (numberOfNegative < numberOfPositive) {
            Dot[] convexPolygon = new Dot[numberOfDots - numberOfNegative];
            int j = 0;
            for (int i = 0; i < polygon.length; i++) {
                if (polygon[i].isPositive() == true) {
                    convexPolygon[j] = polygon[i];
                    System.out.println("Convex polygon: dot added: (" + convexPolygon[j].getX() + "," + convexPolygon[j].getY() + ")");
                    j++;
                }
            }
            this.convexPolygon = convexPolygon;
            numberOfDots -= numberOfNegative;
            System.out.println("Number of dots final: " + numberOfDots);
        } else if (numberOfNegative > numberOfPositive) {
            Dot[] convexPolygon = new Dot[numberOfDots - numberOfPositive];
            int j = 0;
            for (int i = 0; i < polygon.length; i++) {
                if (polygon[i].isPositive() == false) {
                    convexPolygon[j] = polygon[i];
                    System.out.println("Convex polygon: dot added: (" + convexPolygon[j].getX() + "," + convexPolygon[j].getY() + ")");
                    j++;
                }
            }
            this.convexPolygon = convexPolygon;
            this.numberOfDots -= numberOfPositive;
            System.out.println("Number of dots final: " + numberOfDots);
        } else {
            System.out.println("This is Star Polygon");
            int j = 0, k = 0;
            double perimeterPositive = 0;
            double perimeterNegative = 0;
            Dot[] polygonPositive = new Dot[numberOfDots - numberOfNegative];
            Dot[] polygonNegative = new Dot[numberOfDots - numberOfPositive];
            for (int i = 0; i < polygon.length; i++) {
                if (polygon[i].isPositive()) {
                    polygonPositive[j] = polygon[i];
                    System.out.println("Positive polygon: dot added: (" + polygonPositive[j].getX() + "," + polygonPositive[j].getY() + ")");
                    j++;
                } else {
                    polygonNegative[k] = polygon[i];
                    System.out.println("Negative polygon: dot added: (" + polygonNegative[k].getX() + "," + polygonNegative[k].getY() + ")");
                    k++;
                }
            }
            for (int i = 0; i < polygonPositive.length; i++) {
                if (i == polygonPositive.length - 1) {
                    perimeterPositive += LineSegment.segmentLength(polygonPositive[i], polygonPositive[0]);
                } else {
                    perimeterPositive += LineSegment.segmentLength(polygonPositive[i], polygonPositive[i + 1]);
                }
            }
            for (int i = 0; i < polygonNegative.length; i++) {
                if (i == polygonNegative.length - 1) {
                    perimeterNegative += LineSegment.segmentLength(polygonNegative[i], polygonNegative[0]);
                } else {
                    perimeterNegative += LineSegment.segmentLength(polygonNegative[i], polygonNegative[i + 1]);
                }
            }
            if (perimeterPositive < perimeterNegative) {
                this.convexPolygon = polygonPositive;
                this.numberOfDots -= numberOfNegative;
                System.out.println("Positive polygon is smaller, set as Convex Polygon");
                System.out.println("Number of dots final: " + numberOfDots);
            } else {
                this.convexPolygon = polygonNegative;
                this.numberOfDots -= numberOfPositive;
                System.out.println("Negative polygon is smaller, set as Convex Polygon");
                System.out.println("Number of dots changed: " + numberOfDots);
            }
        }
    }

    Dot[] getConvexPolygon() {
        return convexPolygon;
    }

    void setSegmentsOfPolygon(Dot[] convexPolygon) {
        LineSegment[] segmentsOfPolygon = new LineSegment[convexPolygon.length];
        for (int i = 0; i < segmentsOfPolygon.length; i++) {
            if (i == segmentsOfPolygon.length - 1) {
                segmentsOfPolygon[i] = new LineSegment(convexPolygon[i], convexPolygon[0]);
                System.out.println("Segment: " + (i + 1) + " set");
            } else {
                segmentsOfPolygon[i] = new LineSegment(convexPolygon[i], convexPolygon[i + 1]);
                System.out.println("Segment: " + (i + 1) + " set");
            }
        }
        this.segmentsOfPolygon = segmentsOfPolygon;
    }

    void setGun() {
        System.out.println("Enter Coordinates x,y of a gun location: ");
        System.out.println("x: ");
        Scanner in2 = new Scanner(System.in);
        double xGun = in2.nextDouble();
        in2.nextLine();
        System.out.println("y: ");
        double yGun = in2.nextDouble();
        Dot gun = new Dot(xGun, yGun);
        this.gun = gun;
        int count = 0;
        setIndexOfSegmentOnLineWithGun();
        for (int i = 0; i < segmentsOfPolygon.length; i++) {
            if (Line.isDotOnLine(segmentsOfPolygon[i], this.gun)) {

                System.out.println("--------------------------------------");
                System.out.println("Gun is on same line with segment " + i);
                System.out.println("--------------------------------------");
                this.indexOfSegmentOnLineWithGun[count] = i;
                count++;
            }
        }
    }

    void setMostRemoteDots() {
        double maxAngle = 0;
        int dotIndex1 = 0, dotIndex2 = 0;
        for (int i = 0; i < convexPolygon.length; i++) {
            for (int j = i + 1; j < convexPolygon.length; j++) {
                if (Dot.getAngleABC(convexPolygon[i], gun, convexPolygon[j]) > maxAngle) {
                    maxAngle = Dot.getAngleABC(convexPolygon[i], gun, convexPolygon[j]);
                    dotIndex1 = i;
                    dotIndex2 = j;
                }
            }
        }
        this.mostRemoteDot1 = convexPolygon[dotIndex1];
        this.mostRemoteDot2 = convexPolygon[dotIndex2];
        this.maxAngle = maxAngle;
        System.out.println("Max Angle is: " + this.maxAngle);
        System.out.println("Most remote dots are: " + dotIndex1 + ", " + dotIndex2);
        System.out.println("--------------------------------------");
    }

    LineSegment[] getSidesOfQuadrangle() {
        return sidesOfQuadrangle;
    }

    void setSidesOfQuadrangle(int n) {
        int numberOfAdditionalLines = convexPolygon.length - 2;
        int number = n + numberOfAdditionalLines;
        System.out.println("--------------------------------------");
        System.out.println("numberOfVerticesBetweenMostRemote " + numberOfAdditionalLines);
        System.out.println("Number of segments for Quadrangle " + number);
        System.out.println("--------------------------------------");
        Line[] lines = new Line[number];
        LineSegment[] sidesOfQuadrangle = new LineSegment[number];
        Dot[] dots = new Dot[number];
        Dot dotOnFirstLine = new Dot(Double.MAX_VALUE, Double.MAX_VALUE);
        Dot dotOnSecondLine = new Dot(Double.MAX_VALUE, Double.MAX_VALUE);

        Dot[] LineCrossSegment = new Dot[2];
        int count = 0;
        double k, xCross = Double.MAX_VALUE, yCross = Double.MAX_VALUE;
        double distanceToFirstRemote = LineSegment.segmentLength(gun, mostRemoteDot1);
        double distanceToSecondRemote = LineSegment.segmentLength(gun, mostRemoteDot2);

        if (distanceToFirstRemote == distanceToSecondRemote) {
            dotOnFirstLine = mostRemoteDot1;
            dotOnSecondLine = mostRemoteDot2;
        } else if (distanceToFirstRemote < distanceToSecondRemote) {
            k = distanceToFirstRemote / distanceToSecondRemote;
            dotOnFirstLine = mostRemoteDot1;
            dotOnSecondLine.setXY(gun.getX() + k * (mostRemoteDot2.getX() - gun.getX()), gun.getY() + k * (mostRemoteDot2.getY() - gun.getY()));
        } else {
            k = distanceToSecondRemote / distanceToFirstRemote;
            dotOnSecondLine = mostRemoteDot2;
            dotOnFirstLine.setXY(gun.getX() + k * (mostRemoteDot1.getX() - gun.getX()), gun.getY() + k * (mostRemoteDot1.getY() - gun.getY()));
        }
        double x;
        double y;
        for (int i = 0; i < n; i++) {
            x = dotOnFirstLine.getX() + (dotOnSecondLine.getX() - dotOnFirstLine.getX()) * (i + 1) / (n + 1);
            y = dotOnFirstLine.getY() + (dotOnSecondLine.getY() - dotOnFirstLine.getY()) * (i + 1) / (n + 1);
            dots[i] = new Dot(x, y);
            lines[i] = new Line(this.gun, dots[i]);

            for (int j = 0; j < segmentsOfPolygon.length; j++) {
                if (lines[i].getK() == segmentsOfPolygon[j].getK()) {
                    System.out.println("Line " + (i + 1) + "from gun and segment " + (j + 1) + " of polygon don't cross.");
                } else if (segmentsOfPolygon[j].isVertical()) {

                    xCross = segmentsOfPolygon[j].getDot1().getX();
                    yCross = lines[i].getK() * xCross + lines[i].getB1();

                } else {
                    xCross = (segmentsOfPolygon[j].getB1() - lines[i].getB1()) / (lines[i].getK() - segmentsOfPolygon[j].getK());
                    yCross = (lines[i].getK() * segmentsOfPolygon[j].getB1() - segmentsOfPolygon[j].getK() * lines[i].getB1()) / (lines[i].getK() - segmentsOfPolygon[j].getK());
                }

                if (xCross >= Math.min(segmentsOfPolygon[j].getDot1().getX(), segmentsOfPolygon[j].getDot2().getX())
                        && xCross <= Math.max(segmentsOfPolygon[j].getDot1().getX(), segmentsOfPolygon[j].getDot2().getX())
                        && yCross >= Math.min(segmentsOfPolygon[j].getDot1().getY(), segmentsOfPolygon[j].getDot2().getY())
                        && yCross <= Math.max(segmentsOfPolygon[j].getDot1().getY(), segmentsOfPolygon[j].getDot2().getY())) {
                    System.out.println();
                    LineCrossSegment[count] = new Dot(xCross, yCross);
                    count++;
                }
            }

            count = 0;
            sidesOfQuadrangle[i] = new LineSegment(LineCrossSegment[0], LineCrossSegment[1]);
            System.out.println("Side of quadrangle " + i + " created (fan): (" + LineCrossSegment[0].getX() + ", " + LineCrossSegment[0].getY() + "), (" + LineCrossSegment[1].getX()
                    + ", " + LineCrossSegment[1].getY() + ")");
        }
        if (indexOfSegmentOnLineWithGun[0] > -1 && indexOfSegmentOnLineWithGun[1] > -1) {

            sidesOfQuadrangle[n] = segmentsOfPolygon[indexOfSegmentOnLineWithGun[0]];
            sidesOfQuadrangle[n + 1] = segmentsOfPolygon[indexOfSegmentOnLineWithGun[1]];
            System.out.println("Side of quadrangle " + n + " created (segment on line with gun): (" + sidesOfQuadrangle[n].getDot1().getX() + ", " + sidesOfQuadrangle[n].getDot1().getY() + "), ("
                    + sidesOfQuadrangle[n].getDot2().getX()
                    + ", " + sidesOfQuadrangle[n].getDot2().getY() + ")");
            System.out.println("Side of quadrangle " + (n + 1) + " created (segment on line with gun): (" + sidesOfQuadrangle[n + 1].getDot1().getX() + ", " + sidesOfQuadrangle[n + 1].getDot1().getY() + "), ("
                    + sidesOfQuadrangle[n + 1].getDot2().getX()
                    + ", " + sidesOfQuadrangle[n + 1].getDot2().getY() + ")");
            for (int i = 0; i < convexPolygon.length; i++) {
                Dot lineCrossSeg;
                if (convexPolygon[i] != segmentsOfPolygon[indexOfSegmentOnLineWithGun[0]].getDot1() &&
                        convexPolygon[i] != segmentsOfPolygon[indexOfSegmentOnLineWithGun[0]].getDot2() &&
                        convexPolygon[i] != segmentsOfPolygon[indexOfSegmentOnLineWithGun[1]].getDot1() &&
                        convexPolygon[i] != segmentsOfPolygon[indexOfSegmentOnLineWithGun[1]].getDot2()) {
                    dots[n + 2 + count] = convexPolygon[i];
                    lines[n + 2 + count] = new Line(this.gun, dots[n + 2 + count]);

                    for (int j = 0; j < segmentsOfPolygon.length; j++) {
                        lineCrossSeg = LineSegment.lineCrossSegment(lines[n + 2 + count], segmentsOfPolygon[j]);
                        if (LineSegment.isDotOnLineSegment(segmentsOfPolygon[j], lineCrossSeg) && !Dot.isSameDot(lineCrossSeg, dots[n + 2 + count])) {
                            sidesOfQuadrangle[n + 2 + count] = new LineSegment(dots[n + 2 + count], lineCrossSeg);

                            System.out.println("Side of quadrangle " + (n + 2 + count) + " created (vertices): (" + sidesOfQuadrangle[n + 2 + count].getDot1().getX() + ", " + sidesOfQuadrangle[n + 2 + count].getDot1().getY() + "), ("
                                    + sidesOfQuadrangle[n + 2 + count].getDot2().getX()
                                    + ", " + sidesOfQuadrangle[n + 2 + count].getDot2().getY() + ")");
                        }
                    }
                    count++;
                }
            }

        } else if (indexOfSegmentOnLineWithGun[0] > -1) {

            sidesOfQuadrangle[n] = segmentsOfPolygon[indexOfSegmentOnLineWithGun[0]];
            System.out.println("Side of quadrangle " + n + " created (segment on line with gun): (" + sidesOfQuadrangle[n].getDot1().getX() + ", " + sidesOfQuadrangle[n].getDot1().getY() + "), ("
                    + sidesOfQuadrangle[n].getDot2().getX()
                    + ", " + sidesOfQuadrangle[n].getDot2().getY() + ")");
            for (int i = 0; i < convexPolygon.length; i++) {
                Dot lineCrossSeg;
                if (convexPolygon[i] != segmentsOfPolygon[indexOfSegmentOnLineWithGun[0]].getDot1() &&
                        convexPolygon[i] != segmentsOfPolygon[indexOfSegmentOnLineWithGun[0]].getDot2() &&
                        convexPolygon[i] != mostRemoteDot1 &&
                        convexPolygon[i] != mostRemoteDot2) {

                    dots[n + 1 + count] = convexPolygon[i];
                    lines[n + 1 + count] = new Line(this.gun, dots[n + 1 + count]);
                    for (int j = 0; j < segmentsOfPolygon.length; j++) {
                        lineCrossSeg = LineSegment.lineCrossSegment(lines[n + 1 + count], segmentsOfPolygon[j]);
                        if (LineSegment.isDotOnLineSegment(segmentsOfPolygon[j], lineCrossSeg) && !Dot.isSameDot(lineCrossSeg, dots[n + 1 + count])) {

                            sidesOfQuadrangle[n + 1 + count] = new LineSegment(dots[n + 1 + count], lineCrossSeg);

                            System.out.println("Side of quadrangle " + (n + 1 + count) + " created (vertices): (" + sidesOfQuadrangle[n + 1 + count].getDot1().getX() + ", " + sidesOfQuadrangle[n + 1 + count].getDot1().getY() + "), ("
                                    + sidesOfQuadrangle[n + 1 + count].getDot2().getX()
                                    + ", " + sidesOfQuadrangle[n + 1 + count].getDot2().getY() + ")");
                        }
                    }
                    count++;
                }
            }
        } else {
            for (int i = 0; i < convexPolygon.length; i++) {
                Dot lineCrossSeg;
                if (convexPolygon[i] != mostRemoteDot1 &&
                        convexPolygon[i] != mostRemoteDot2) {
                    dots[n + count] = convexPolygon[i];
                    lines[n + count] = new Line(this.gun, dots[n + count]);
                    for (int j = 0; j < segmentsOfPolygon.length; j++) {
                        lineCrossSeg = LineSegment.lineCrossSegment(lines[n + count], segmentsOfPolygon[j]);
                        if (LineSegment.isDotOnLineSegment(segmentsOfPolygon[j], lineCrossSeg) && !Dot.isSameDot(lineCrossSeg, dots[n + count])) {
                            sidesOfQuadrangle[n + count] = new LineSegment(dots[n + count], lineCrossSeg);
                            System.out.println("Side of quadrangle " + (n + count) + " created (vertices): (" + sidesOfQuadrangle[n + count].getDot1().getX() + ", " + sidesOfQuadrangle[n + count].getDot1().getY() + "), ("
                                    + sidesOfQuadrangle[n + count].getDot2().getX()
                                    + ", " + sidesOfQuadrangle[n + count].getDot2().getY() + ")");
                        }
                    }
                    count++;
                }
            }
        }
        this.sidesOfQuadrangle = sidesOfQuadrangle;
    }

    void setTrapezium(LineSegment[] sidesOfQuadrangle) {
        double sMax = 0;
        double sMaxParallelBases = 0;
        LineSegment seg1 = sidesOfQuadrangle[0], seg2 = sidesOfQuadrangle[1];
        LineSegment seg1pb = sidesOfQuadrangle[0], seg2pb = sidesOfQuadrangle[1];
        LineSegment base1 = new LineSegment(sidesOfQuadrangle[0].getDot1(), sidesOfQuadrangle[1].getDot1());
        LineSegment base2 = new LineSegment(sidesOfQuadrangle[0].getDot2(), sidesOfQuadrangle[1].getDot2());
        Dot[] trapezium = new Dot[4];
        for (int i = 0; i < sidesOfQuadrangle.length; i++) {
            for (int j = i + 1; j < sidesOfQuadrangle.length; j++) {
                if (LineSegment.quadrangleArea(sidesOfQuadrangle[i], sidesOfQuadrangle[j]) > sMax) {

                    sMax = LineSegment.quadrangleArea(sidesOfQuadrangle[i], sidesOfQuadrangle[j]);
                    seg1 = sidesOfQuadrangle[i];
                    seg2 = sidesOfQuadrangle[j];
                }
                // check if bases are parallel, it can be max Trapezium even it is not the max Quadrangle
                base1 = new LineSegment(sidesOfQuadrangle[i].getDot1(), sidesOfQuadrangle[j].getDot2());
                base2 = new LineSegment(sidesOfQuadrangle[i].getDot2(), sidesOfQuadrangle[j].getDot1());

                if ((Math.abs(base1.getK() - base2.getK()) * 100) / 100 == 0) {
                    if (LineSegment.segmentLength(base1) < LineSegment.segmentLength(base2)) {
                        if (LineSegment.trapeziumArea(base1, base2) > sMaxParallelBases) {

                            sMaxParallelBases = LineSegment.trapeziumArea(base1, base2);
                            seg1pb = sidesOfQuadrangle[i];
                            seg2pb = sidesOfQuadrangle[j];
                        }
                    } else {
                        if (LineSegment.trapeziumArea(base2, base1) > sMaxParallelBases) {

                            sMaxParallelBases = LineSegment.trapeziumArea(base2, base1);
                            seg1pb = sidesOfQuadrangle[i];
                            seg2pb = sidesOfQuadrangle[j];
                        }
                    }
                }
            }
        }
        System.out.println("--------------------------------------");
        System.out.println("Max Quadrangle Area is: " + sMax);
        System.out.println("Dots: (" + seg1.getDot1().getX() + ", " + seg1.getDot1().getY() + ") ("
                + seg1.getDot2().getX() + ", " + seg1.getDot2().getY() + ") ("
                + seg2.getDot1().getX() + ", " + seg2.getDot1().getY() + ") ("
                + seg2.getDot2().getX() + ", " + seg2.getDot2().getY() + ") (");

        Dot close1, close2, distant1, distant2, trapeziumDot1, trapeziumDot2;
        if (LineSegment.segmentLength(gun, seg1.getDot1()) < LineSegment.segmentLength(gun, seg1.getDot2())) {
            close1 = seg1.getDot1();
            distant1 = seg1.getDot2();
            System.out.println("------------------------------");

        } else {
            close1 = seg1.getDot2();
            distant1 = seg1.getDot1();

        }
        if (LineSegment.segmentLength(gun, seg2.getDot1()) < LineSegment.segmentLength(gun, seg2.getDot2())) {
            close2 = seg2.getDot1();
            distant2 = seg2.getDot2();

        } else {
            close2 = seg2.getDot2();
            distant2 = seg2.getDot1();

        }
        LineSegment distantSegment = new LineSegment(distant1, distant2);
        if (distantSegment.isVertical()) {
            trapeziumDot1 = new Dot(close1.getX(), seg2.getK() * close1.getX() + seg2.getB1());
            trapeziumDot2 = new Dot(close2.getX(), seg1.getK() * close1.getX() + seg1.getB1());
        } else {
            Line closeLine1 = new Line(distantSegment.getK(), close1.getY() - distantSegment.getK() * close1.getX());
            Line closeLine2 = new Line(distantSegment.getK(), close2.getY() - distantSegment.getK() * close2.getX());

            trapeziumDot1 = (seg2.isVertical()) ?
                    new Dot(seg2.getDot1().getX(), closeLine1.getK() * seg2.getDot1().getX() + close1.getY() + closeLine1.getK() * close1.getX()) :
                    new Dot((seg2.getB1() - closeLine1.getB1()) / (closeLine1.getK() - seg2.getK()), (closeLine1.getK() * seg2.getB1() - seg2.getK() * closeLine1.getB1()) / (closeLine1.getK() - seg2.getK()));
            trapeziumDot2 = (seg1.isVertical()) ?
                    new Dot(seg1.getDot1().getX(), closeLine2.getK() * seg1.getDot1().getX() + close2.getY() + closeLine2.getK() * close2.getX()) :
                    new Dot((seg1.getB1() - closeLine2.getB1()) / (closeLine2.getK() - seg1.getK()), (closeLine2.getK() * seg1.getB1() - seg1.getK() * closeLine2.getB1()) / (closeLine2.getK() - seg1.getK()));
        }
        if (LineSegment.isDotOnLineSegment(seg2, trapeziumDot1)) {
            trapezium[0] = trapeziumDot1;
            trapezium[1] = close1;
            trapezium[2] = distant1;
            trapezium[3] = distant2;
        } else {
            trapezium[0] = close2;
            trapezium[1] = trapeziumDot2;
            trapezium[2] = distant1;
            trapezium[3] = distant2;
        }
        this.trapezium = trapezium;
        System.out.println("****************************************************");
        System.out.println("Trapezium Dots: (" + this.trapezium[0].getX() + ", " + this.trapezium[0].getY() + ") ("
                + this.trapezium[1].getX() + ", " + this.trapezium[1].getY() + ") ("
                + this.trapezium[2].getX() + ", " + this.trapezium[2].getY() + ") ("
                + this.trapezium[3].getX() + ", " + this.trapezium[3].getY() + ")");
        LineSegment shortSide = new LineSegment(this.trapezium[0], this.trapezium[1]);
        LineSegment longSide = new LineSegment(this.trapezium[2], this.trapezium[3]);
        System.out.println("Area of Trapezium built into Max Quadrangle (formula for Trapezium): " + LineSegment.trapeziumArea(shortSide, longSide));
        if (sMaxParallelBases > LineSegment.trapeziumArea(shortSide, longSide)) {
            System.out.println("NOTE: There is also Quadrangle which is not Max Quadrangle, but it is Trapezium as it has parallel bases.");
            System.out.println("It's Area is bigger than Area of Trapezium built into Max Quadrangle.");
            System.out.println("Max Quadrangle Area with parallel bases is: " + sMaxParallelBases);
            System.out.println("Dots: (" + seg1pb.getDot1().getX() + ", " + seg1pb.getDot1().getY() + ") ("
                    + seg1pb.getDot2().getX() + ", " + seg1pb.getDot2().getY() + ") ("
                    + seg2pb.getDot1().getX() + ", " + seg2pb.getDot1().getY() + ") ("
                    + seg2pb.getDot2().getX() + ", " + seg2pb.getDot2().getY() + ") (");


        }
    }
}