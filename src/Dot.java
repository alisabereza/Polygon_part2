public class Dot {
    private double x, y;
    private boolean isPositive;

    void setPositive(boolean positive) {
        isPositive = positive;
    }

    boolean isPositive() {
        return isPositive;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Dot(double x, double y) {
        this.x = x;
        this.y = y;
    }

    static double ProductOfTwoVectors(Dot dot1, Dot dot2, Dot dot3) {
        double product = dot1.getX() * dot2.getY() + dot3.getX() * dot1.getY() + dot2.getX() * dot3.getY() - dot3.getX() * dot2.getY() - dot2.getX() * dot1.getY() - dot1.getX() * dot3.getY();
        return product;
    }

    static double getAngleABC(Dot dot1, Dot dot2, Dot dot3) {
        double abx = dot2.getX() - dot1.getX();
        double aby = dot2.getY() - dot1.getY();
        double cbx = dot2.getX() - dot3.getX();
        double cby = dot2.getY() - dot3.getY();
        double dot = abx * cbx + aby * cby;
        double abSqr = abx * abx + aby * aby;
        double cbSqr = cbx * cbx + cby * cby;
        double cosSqr = dot * dot / abSqr / cbSqr;
        double cos2 = 2 * cosSqr - 1;
        double alpha2 = (cos2 <= -1) ? Math.PI : (cos2 >= 1) ? 0 : Math.acos(cos2);
        double rslt = alpha2 / 2;
        double rs = rslt * 180. / Math.PI;
        if (dot < 0) {
            rs = 180 - rs;
        }
        double det = abx * cby - aby * cby;
        if (dot < 0) {
            rs = -rs;
        }
        return Math.floor(rs + 0.5);

    }

    static boolean isSameDot(Dot dot1, Dot dot2) {
        if (dot1.getX() == dot2.getX() && dot1.getY() == dot2.getY()) {
            return true;
        } else return false;
    }

}