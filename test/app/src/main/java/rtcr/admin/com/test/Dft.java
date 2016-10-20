package rtcr.admin.com.test;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by B D Gondane on 20-10-2016.
 */
public class Dft {
    private Context mContext;
    int count=0;
    StringBuilder s1=new StringBuilder(1000);
    String complex;
    public void splitdata(Context context)
    {


        double[] X= new double[1000];
        double[] Y= new double[1000];
        double[] Y1= new double[1000];
        double[] X1= new double[1000];

        int i=0;
        try {
            InputStream ins = context.getResources().openRawResource(R.raw.datacoord);
            //FileReader fr=new FileReader("C:\\Users\\B D Gondane\\Desktop\\Major\\Code\\test\\app\\src\\main\\res\\raw\\datacoord.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] temp = (line.split(","));
                X[i]=Double.parseDouble(temp[0]);
                Y[i]=Double.parseDouble(temp[1]);

                i++;
                count++;

            }
            ;for (i = 0; i < 50; i++) {
                Log.d("X:", String.valueOf(X[i]));
                Log.d("Y:", String.valueOf(Y[i]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        dft(X,Y,X1,Y1);
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

        }
        for(int i=0;i<n;i++)
        {
            complex=outreal[i]+","+outimag[i];
            s1.append(complex);
            s1.append("\n");
        }
    }


}



