public class Line {
    // line: ax + by + c = 0
    private double a, b, c;
    //line y = kx + b
    private double k, b1;

    double getK() {
        return k;
    }

    double getB1() {
        return b1;
    }

    Line(double k, double b1) {
        this.k = k;
        this.b1 = b1;
    }

    double getA() {
        return a;
    }

    double getB() {
        return b;
    }

    double getC() {
        return c;
    }

    Line(Dot dot1, Dot dot2) {
        //line y = kx + b
        if (dot1.getX() == dot2.getX()) {
            this.k = Double.MAX_VALUE;
            this.b1 = Double.MAX_VALUE;
        } else {
            this.k = (dot2.getY() - dot1.getY()) / (dot2.getX() - dot1.getX());
            this.b1 = (dot2.getX() * dot1.getY() - dot1.getX() * dot2.getY()) / (dot2.getX() - dot1.getX());
        }
        // line: ax + by + c = 0
        this.a = dot2.getY() - dot1.getY();
        this.b = dot1.getX() - dot2.getY();
        this.c = dot2.getX() * dot1.getY() - dot1.getX() * dot2.getY();
    }

    static void lineInfo(Line line) {
        System.out.println("Line is: " + line.getK() + "*x + " + line.getB1());
    }

    static double distanceBetweenParallel(LineSegment seg1, LineSegment seg2) {
        double distance;
        if (seg1.getDot1().getX() == seg1.getDot2().getX()) {
            distance = Math.abs(seg1.getDot1().getX() - seg2.getDot1().getX());
        } else {
            double divider = seg1.getK() * seg1.getK() + 1;
            distance = Math.sqrt(Math.pow((seg1.getB1() * seg1.getK() - seg2.getB1() * seg1.getK()) / divider, 2) + Math.pow((seg2.getB1() - seg1.getB1()) / divider, 2));
        }

        return distance;
    }

    static boolean isDotOnLine(LineSegment seg, Dot dot) {

        if (seg.getDot1().getX() == seg.getDot2().getX() && dot.getX() == seg.getDot1().getX()) {
            return true;
        } else if (Math.round((seg.getK() * dot.getX() + seg.getB1() - dot.getY()) * 1000) / 1000 == 0) {
            return true;
        } else {
            return false;
        }
    }

}