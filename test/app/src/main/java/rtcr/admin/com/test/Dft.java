package rtcr.admin.com.test;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;

/**
 * Created by B D Gondane on 20-10-2016.
 */
public class Dft {
    private Context mContext;
    int count=0;
    StringBuilder s1=new StringBuilder(1000);
    String complex;

    public void normalize(Context context,String pixel[],int pixelCount)
    {
        int ii=0,jj;
        double sumX,sumY,n;

        while(ii<pixelCount)
        {
            sumX=0;
            sumY=0;
            jj=ii-4;
            n=0;
            String line[]=new String[3];
            while(jj<ii+4 && jj<pixelCount)
            {
                if(jj>=0)
                { line=new String[3];
                    line=pixel[jj].split(",")
                   ;
                    sumX=sumX+(Double.parseDouble(line[0]));
                    sumY=sumY+(Double.parseDouble(line[1]));
                    n++;
                }

                jj++;
            }
            sumX=sumX/n;
            sumY=sumY/n;

            pixel[ii]=sumX+","+sumY+","+line[2];

            Log.d("normalize:", pixel[ii]+","+ii);

            ii++;
        }

        scaling(context,pixel,pixelCount);
    }
    public void scaling(Context context,String pixel[],int pixelCount)
    {
        double ScaleX,ScaleY,n,Xmax=0,Xmin=99999;
        ScaleX=0;
        ScaleY=0;
        int ii=0,jj=0;
        String line[]=new String[3];

        while(ii<pixelCount) {
            line = pixel[jj].split(",");
            ScaleX = (Double.parseDouble(line[0]));
            ScaleY = (Double.parseDouble(line[1]));
            if (ScaleX > Xmax) {
                Xmax = ScaleX;
            } else if (ScaleX < Xmin) {
                Xmin = ScaleX;
            }
            jj++;ii++;
        }
        ii=0;jj=0;
            //Xmax=Xmax-Xmin;
        while(ii<pixelCount){
            line = pixel[jj].split(",");
            ScaleX = (Double.parseDouble(line[0]));
            ScaleY = (Double.parseDouble(line[1]));
            ScaleX=((ScaleX-Xmin)/Xmax);

            pixel[ii]=ScaleX+","+ScaleY+","+line[2];
            Log.d("Scaled", pixel[ii]+","+ii);
            ii++;
            jj++;

        }
        splitdata(context, pixel, pixelCount);


    }

    public void splitdata(Context context,String pixel[],int pixelCount)
    {


        double[] X;
        double[] Y;
        double[] Y1= new double[1000];
        double[] X1= new double[1000];

        int i=0;
        try {
        //    InputStream ins = context.getResources().openRawResource(R.raw.datacoord);
            //FileReader fr=new FileReader("C:\\Users\\B D Gondane\\Desktop\\Major\\Code\\test\\app\\src\\main\\res\\raw\\datacoord.txt");
       //     BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            String line;
            int ii=0;
            int jj=1;
          //  while ((line = reader.readLine()) != null) {
            while(ii<pixelCount)
            {
                X= new double[1000];
                Y= new double[1000];

                count=0;
                i=0;
                line=pixel[ii];
                String[] temp = (line.split(","));
                jj=ii;
                while(jj<pixelCount)
                {
                    String temp1[]=pixel[jj].split(",");
                    if(temp[2].equals(temp1[2]))
                    {
                        X[i]=Double.parseDouble(temp1[0]);
                        Y[i]=Double.parseDouble(temp1[1]);
                        Log.d("X:", String.valueOf(X[i]));
                        Log.d("Y:", String.valueOf(Y[i]));
                        count++;
                        i++;
                    }
                    else{

                        break;
                    }
                    jj++;
                }
                dft(X,Y,X1,Y1);



                ii=jj;


            }
            /*
            for (i = 0; i < 50; i++) {
                Log.d("X:", String.valueOf(X[i]));
                Log.d("Y:", String.valueOf(Y[i]));
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File dftfile = new File(Environment.getExternalStorageDirectory().toString()+"/dft.txt");

            if(!dftfile.exists())
            {
                dftfile.createNewFile();

            }
            FileWriter fw =new FileWriter(dftfile,true);
            //fw.append(text);
            //fw.append(text1);
            fw.append("=======\n");

            fw.append(s1);
            fw.flush();
            fw.close();
            Toast.makeText(context, "DFT calculated", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            //Toast.makeText(getBaseContext(), e.getMessage(),
                   // Toast.LENGTH_SHORT).show();
        }

    }
    void dft(double[] inreal, double[] inimag, double[] outreal, double[] outimag) {
        int n = count;
        for (int k = 0; k < n; k++) {  // For each output element
            double sumreal = 0;
            double sumimag = 0;
            for (int t = 0; t < n; t++) {  // For each input element
                double angle = 2 * Math.PI * t * k / n;
                sumreal +=  inreal[t] * Math.cos(angle) + inimag[t] * Math.sin(angle);
                sumimag += -inreal[t] * Math.sin(angle) + inimag[t] * Math.cos(angle);
            }
            outreal[k] = sumreal;
            outimag[k] = sumimag;
            Log.d("real:", String.valueOf(sumreal));
            Log.d("img:", String.valueOf(sumimag));
        }
        for(int i=0;i<n;i++)
        {
            complex=outreal[i]+","+outimag[i];
            s1.append(complex);
            s1.append("\n");
        }
        s1.append("********************\n");
    }


}


