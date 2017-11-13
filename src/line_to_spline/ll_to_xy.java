package line_to_spline;

import java.io.Console;

/**
 * Created by delic on 28.10.2017.
 */
public final class ll_to_xy {
    private ll_to_xy(){}
    private static double RADIANS = 57.2957795;

    private static double DEG_TO_RADIANS(double x) {
        return (x / RADIANS);
    }

    private static double METERS_DEGLON(double x) {
        double d2r = DEG_TO_RADIANS(x);
        return ((111415.13 * Math.cos(d2r)) - (94.55 * Math.cos(3.0 * d2r)) + (0.12 * Math.cos(5.0 * d2r)));

    }

    private static double METERS_DEGLAT(double x) {
        double d2r = DEG_TO_RADIANS(x);
        return (111132.09 - (566.05 * Math.cos(2.0 * d2r)) + (1.20 * Math.cos(4.0 * d2r)) - (0.002 * Math.cos(6.0 * d2r)));
    }

    public static double[] lat_long_to_xy(double source_lat, double source_lon, double origin_lat, double origin_lon) {
        double x, y;
        y = (source_lon - origin_lon) * METERS_DEGLON(origin_lat);
        x = (source_lat - origin_lat) * METERS_DEGLAT(origin_lat);
        double r = Math.sqrt(x * x + y * y);
        System.out.println(r);
        System.out.println("ll_to_xy: x="+x+", y="+y);
        double ct = x / r;
        double st = y / r;
        double rotation_angle_degs = 0;
        double angle = DEG_TO_RADIANS(rotation_angle_degs);
        x = r * ((ct * Math.cos(angle)) + (st * Math.sin(angle)));
        y = r * ((st * Math.cos(angle)) - (ct * Math.sin(angle)));
        double xoffset_mtrs = 0;
        double yoffset_mtrs = 0;
        //double pxpos_mtrs = x + xoffset_mtrs;
        //double pypos_mtrs = y + yoffset_mtrs;
        //System.out.println("ll_to_xy: x="+pxpos_mtrs+", y="+pypos_mtrs);
        return new double[]{Math.abs(x), Math.abs(y)};
    }
}
