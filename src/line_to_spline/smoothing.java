package line_to_spline;


import java.util.ArrayList;

public final class smoothing {
    private smoothing(){}
    private static boolean check(double x1, double y1, double x2, double y2, double x3, double y3){
        double eps=10;
        double _x1=(x2-x1);
        double _x2=(x2-x3);
        double _y1=(y2-y1);
        double _y2=(y2-y3);
        double pseudoscalar = _x1*_y2-_x2*_y1;
        double length_1 = Math.sqrt(_x1*_x1+_y1*_y1);
        double length_2 = Math.sqrt(_x2*_x2+_y2*_y2);
        double tst = Math.asin(Math.abs(pseudoscalar/(length_1*length_2)))*180/Math.PI;

        return Math.asin(Math.abs(pseudoscalar/(length_1*length_2)))*180/Math.PI>eps;
    }
    public static ArrayList<ArrayList<Integer>> go(ArrayList<ArrayList<Double>> _way_x,ArrayList<ArrayList<Double>> _way_y)
    {   Integer count=0;
        int lookback=5;
        ArrayList<ArrayList<Integer>> colors = new ArrayList<>(_way_x.size());
        for (int i=0;i<_way_x.size();i++) {
            colors.add(i, new ArrayList<Integer>(_way_x.get(i).size()));
            for (int j = 0; j < _way_x.get(i).size(); j++)
            {
                colors.get(i).add(j,-1);
            }
        }
        for (int i=0;i<_way_x.size();i++) {
            for (int j = 1; j < _way_x.get(i).size() - 1; j++) {
                if ((check(_way_x.get(i).get(j - 1), _way_y.get(i).get(j - 1), _way_x.get(i).get(j), _way_y.get(i).get(j), _way_x.get(i).get(j + 1), _way_y.get(i).get(j + 1)))) {
                    if (colors.get(i).get(j - 1) < 0) {
                        count++;
                        colors.get(i).set(j - 1, count);
                        colors.get(i).set(j, count);
                        colors.get(i).set(j + 1, count);
                    } else if (colors.get(i).get(j) > 0) {
                        colors.get(i).set(j + 1, colors.get(i).get(j));
                    } else {
                        count++;
                        colors.get(i).set(j, count);
                        colors.get(i).set(j + 1, count);
                    }
                }
                if (j > 1)
                    if (colors.get(i).get(j - 2) < 0)
                        colors.get(i).set(j - 2, 0);
                
                if (j>lookback)
                {
                    
                }
            }
            
            

            if (colors.get(i).get(colors.get(i).size()-3) < 0)
                colors.get(i).set(colors.get(i).size()-3,0);

            if (colors.get(i).get(colors.get(i).size()-2) < 0)
                colors.get(i).set(colors.get(i).size()-2,0);

            if (colors.get(i).get(colors.get(i).size()-1) < 0)
                colors.get(i).set(colors.get(i).size()-1,0);
        }
        return colors;
    }
    public static double[][] go (double[][] points){
        int count=0;
        for (int i=1;i<points[0].length-1;i++)
        {

            if((check(points[0][i-1],points[1][i-1],points[0][i],points[1][i],points[0][i+1],points[1][i+1])))
            {
                if (points[2][i-1]<0)
                {
                    count++;
                    points[2][i-1]=count;
                    points[2][i]=count;
                    points[2][i+1]=count;
                }else if (points[2][i]>0)
                {
                    points[2][i+1]=points[2][i];
                }else
                {
                    count++;
                    points[2][i]=count;
                    points[2][i+1]=count;
                }
            }
            if (i>1)
                if (points[2][i-2]<0)
                    points[2][i-2]=0;
        }
        if (points[2][points[2].length-3]<0)
            points[2][points[2].length-3]=0;
        if (points[2][points[2].length-2]<0)
            points[2][points[2].length-2]=0;
        if (points[2][points[2].length-1]<0)
           points[2][points[2].length-1]=0;
        return points;
    }
}

