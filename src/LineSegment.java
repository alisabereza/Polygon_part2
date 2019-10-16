import java.sql.SQLOutput;

public class LineSegment {

    private Dot dot1, dot2;
    private double k, b1;

    double getK() {
        return k;
    }

    double getB1() {
        return b1;
    }

    LineSegment(Dot dot1, Dot dot2) {
        this.dot1 = dot1;
        this.dot2 = dot2;
        if (dot1.getX() == dot2.getX()) {
            this.k = Double.MAX_VALUE;
            this.b1 = Double.MAX_VALUE;
        } else {
            this.k = (dot2.getY() - dot1.getY()) / (dot2.getX() - dot1.getX());
            this.b1 = (dot2.getX() * dot1.getY() - dot1.getX() * dot2.getY()) / (dot2.getX() - dot1.getX());
        }
    }

    Dot getDot1() {
        return dot1;
    }

    Dot getDot2() {
        return dot2;
    }

    static double segmentLength(Dot dot1, Dot dot2) {
        return Math.sqrt(Math.pow((dot2.getX() - dot1.getX()), 2) + Math.pow((dot2.getY() - dot1.getY()), 2));
    }

    static double segmentLength(LineSegment seg) {
        return Math.sqrt(Math.pow((seg.getDot2().getX() - seg.getDot1().getX()), 2) + Math.pow((seg.getDot2().getY() - seg.getDot1().getY()), 2));
    }

    static double quadrangleArea(LineSegment seg1, LineSegment seg2) {
        double a = segmentLength(seg1);
        double b = (segmentLength(seg1.dot2, seg2.dot1) < segmentLength(seg1.dot1, seg2.dot1)) ? segmentLength(seg1.dot2, seg2.dot1) : segmentLength(seg1.dot1, seg2.dot1);
        double c = segmentLength(seg2);
        double d = (segmentLength(seg2.dot2, seg1.dot2) < segmentLength(seg2.dot2, seg1.dot1)) ? segmentLength(seg2.dot2, seg1.dot2) : segmentLength(seg2.dot2, seg1.dot1);
        double p = (a + b + c + d) / 2;
        double area = Math.sqrt((p - a) * (p - b) * (p - c) * (p - d));
        return area;
    }

    static double trapeziumArea(LineSegment shortSide, LineSegment longSide) {
        return (LineSegment.segmentLength(shortSide) + LineSegment.segmentLength(longSide)) * Line.distanceBetweenParallel(shortSide, longSide) / 2;
    }

    static boolean isDotOnLineSegment(LineSegment seg, Dot dot) {
        if (Line.isDotOnLine(seg, dot)
                && (dot.getX() >= Math.min(seg.getDot1().getX(), seg.getDot2().getX()))
                && (dot.getX() <= Math.max(seg.getDot1().getX(), seg.getDot2().getX()))
                && (dot.getY() >= Math.min(seg.getDot1().getY(), seg.getDot2().getY()))
                && (dot.getY() <= Math.max(seg.getDot1().getY(), seg.getDot2().getY()))) {
            //System.out.println("Dot on segment");
            return true;
        } else {
            //System.out.println("Dot is not on segment");
            return false;
        }
    }

    boolean isVertical() {
        if (dot1.getX() == dot2.getX()) {
            return true;
        } else {
            return false;
        }
    }

    static Dot lineCrossSegment(Line line, LineSegment seg) {
        double xCross = Double.MAX_VALUE, yCross = Double.MAX_VALUE;
        if (line.getK() == seg.getK()) {
        } else if (seg.isVertical()) {
            xCross = seg.getDot1().getX();
            yCross = line.getK() * xCross + line.getB1();

        } else if (line.getK() == Double.MAX_VALUE) {
            xCross = -line.getC() / line.getA();
            yCross = seg.getK() * xCross + seg.getB1();

        } else {
            xCross = (seg.getB1() - line.getB1()) / (line.getK() - seg.getK());

            yCross = (line.getK() * seg.getB1() - seg.getK() * line.getB1()) / (line.getK() - seg.getK());

        }

        return new Dot(xCross, yCross);
    }
}