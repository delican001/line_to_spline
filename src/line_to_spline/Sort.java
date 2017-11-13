package line_to_spline;

import java.util.ArrayList;

/**
 * Created by delic on 29.10.2017.
 */
public final class Sort {
    public Sort(double[] _x,double[] _y) {
        x=new double[_x.length];
        y=new double[_y.length];

        for (int i=0;i<_x.length;i++)
        {
            x[i]=_x[i];
            y[i]=_y[i];
        }
        result = new double[3][y.length];
        for (int j=0;j<y.length;j++) {
            result[0][j] = x[j];
            result[1][j]=y[j];
            result[2][j]=-1;

        }
        int min=0;
        double min_r=x[0]*x[0]+y[0]*y[0];
        for (int i=0;i<y.length;i++){
            if (min_r>(x[i]*x[i]+y[i]*y[i]))
            {
                min_r=x[i]*x[i]+y[i]*y[i];
                min=i;
            }
        }

        double temp_x=x[0];
        double temp_y=y[0];
        result[0][0]=result[0][min];
        result[1][0]=result[1][min];
        result[0][min]=temp_x;
        result[1][min]=temp_y;
        quickSort();
    }
    public Sort(ArrayList<Double> _x, ArrayList<Double> _y)
    {
        x=new double[_x.size()];
        y=new double[_y.size()];

        for (int i=0;i<_x.size();i++)
        {
            x[i]=_x.get(i);
            y[i]=_y.get(i);
        }
        result = new double[3][y.length];
        for (int j=0;j<y.length;j++) {
            result[0][j] = x[j];
            result[1][j]=y[j];
            result[2][j]=-1;

        }
        int min=0;
        double min_r=x[0]*x[0]+y[0]*y[0];
        for (int i=0;i<y.length;i++){
            if (min_r>(x[i]*x[i]+y[i]*y[i]))
            {
                min_r=x[i]*x[i]+y[i]*y[i];
                min=i;
            }
        }

        double temp_x=x[0];
        double temp_y=y[0];
        result[0][0]=result[0][min];
        result[1][0]=result[1][min];
        result[0][min]=temp_x;
        result[1][min]=temp_y;
        quickSort();
    }
    private double[] x;
    private double[] y;
    private double[][] result;
    private void quickSort() {
        int startIndex = 0;
        int endIndex = x.length -2;
        doSort(startIndex, endIndex);
    }
    private double r(int index){
        return result[0][index]*result[0][index]+result[1][index]*result[1][index];
    }
    private double _r(int index) {return result[0][index];};
    private double __r(int index){
        return (result[0][index]-result[0][index+1])*(result[0][index]-result[0][index+1])+(result[1][index]-result[1][index+1])*(result[1][index]-result[1][index+1]);
        }
    private void doSort(int start, int end) {
        if (start >= end)
            return;
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        while (i < j) {
            while (i < cur && (_r(i) <= _r(cur))) {
                i++;
            }
            while (j > cur && (_r(cur) <= _r(j))) {
                j--;
            }
            if (i < j) {
                double temp_x = result[0][i];
                double temp_y = result[1][i];
                result [0][i]=result[0][j];
                result [1][i]=result[1][j];
                result [0][j]=temp_x;
                result [1][j]=temp_y;
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        doSort(start, cur);
        doSort(cur+1, end);
    }

    public double[][] get_sorted ()
    {
        return  result;
    }

}
