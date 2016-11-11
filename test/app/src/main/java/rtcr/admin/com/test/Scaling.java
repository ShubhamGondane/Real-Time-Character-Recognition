package rtcr.admin.com.test;

/**
 * Created by B D Gondane on 20-10-2016.
 */
public class Scaling
{
    String ss,ss1;
    double xmin=9999,ymin=9999,xmax=0,ymax=0;
    void scale(double x[],double y[],int n)
    {
        for(int i=0;i<n;i++) {
            x[i] = (x[i] - xmin) / xmax;

            //y=(y-ymin)/ymax;
            if (x[i] > xmax) {
                xmax = x[i];
            } else if (x[i] < xmin) {
                xmin = x[i];
            }

            if (y[i] > ymax) {
                ymax = y[i];

            } else if (y[i] < ymin) {
                ymin = y[i];
            }
        }
    }

}
