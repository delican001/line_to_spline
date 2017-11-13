package line_to_spline;

import java.util.ArrayList;

public class Spline {
    public double a,b,c,d;
    public Spline (ArrayList<Double> _x, ArrayList<Double> _y)
    {

    }
    public double f_x(double x)
    {
        return a*x*x*x+b*x*x+c*x+d;
    }
    public double df_dx(double x)
    {
        return 3*a*x*x+2*b*x+c;
    }
}