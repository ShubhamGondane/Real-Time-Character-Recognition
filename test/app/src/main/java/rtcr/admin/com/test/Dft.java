package rtcr.admin.com.test;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by B D Gondane on 20-10-2016.
 */
public class Dft {
    private Context mContext;
    int count=0;
    StringBuilder s1=new StringBuilder(1000);
    StringBuilder s2=new StringBuilder(1000);
    String complex;
    int sampleNo=0;

    Context context1;
    public void normalize(Context context,String pixel[],int pixelCount,int aaa)
    {
        context1=context;
        sampleNo=aaa;
        int ii=0,jj;
        double sumX,sumY,n;
        /*
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
        }*/

        scaling(context,pixel,pixelCount);
    }
    public void scaling(Context context,String pixel[],int pixelCount)
    {
        double ScaleX,ScaleY,n,Xmax=0,Xmin=99999,Ymin=99999,Ymax=0;
        StringBuilder sb=new StringBuilder(1000);
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
            if (ScaleY > Ymax) {
                Ymax = ScaleY;
            } else if (ScaleY < Ymin) {
                Ymin = ScaleY;
            }
            jj++;ii++;
        }
        ii=0;jj=0;
            //Xmax=Xmax-Xmin;
        double yWin=(50/(Xmax-Xmin))*(Ymax-Ymin);
        while(jj<pixelCount){
            line = pixel[jj].split(",");
            ScaleX = (Double.parseDouble(line[0]));
            ScaleY = (Double.parseDouble(line[1]));
            ScaleY=ScaleY*(50/yWin);
            ScaleX = (((ScaleX - Xmin) *49)/ (Xmax-Xmin))+1;

            pixel[jj]=ScaleX+","+ScaleY+","+line[2];
            sb.append(pixel[jj]+"\n");
            Log.d("Scaled", pixel[jj]+","+jj);

            jj++;

        }

        cosWrite(sb,"scale");

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
            while(ii<32)
            {
                X= new double[1000];
                Y= new double[1000];

                count=0;
                i=0;
                line=pixel[ii];
                String[] temp = (line.split(","));
                jj=ii;
                while(jj<32)
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
            Toast.makeText(context1, "DFT calculated", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            //Toast.makeText(getBaseContext(), e.getMessage(),
            // Toast.LENGTH_SHORT).show();
        }

     //   s1.append("********************\n");
        cos();
    }

    void cos()
    {
        double sample[][]= new double[1000][2];

        try {
            BufferedReader br=new BufferedReader(new FileReader(new File(Environment.getExternalStorageDirectory(),"dft.txt")));
            double sample1[][]=new double[1000][2];
            double sample2[][]=new double[1000][2];
            double sample3[][]=new double[1000][2];
            double sample4[][]=new double[1000][2];
            double sample5[][]=new double[1000][2];
            int i=-1,j=0;
            String ss;
            String ll[];
            while((ss=br.readLine())!=null)
            {
                Log.d("read:",String.valueOf(i)+ss);
                Log.d("SampleNO :",String.valueOf(sampleNo));
                if(!ss.contains("=="))
                {
                    switch(i)
                    {
                        case 0:
                            ll=ss.split(",");
                            sample1[j][0]=Double.parseDouble(ll[0]);
                            sample1[j][1]=Double.parseDouble(ll[1]);
                            Log.d("New X:",String.valueOf(sample1[j][0]));
                            Log.d("New Y:",String.valueOf(sample1[j][1]));
                            break;

                        case 1:
                            ll=ss.split(",");
                            sample2[j][0]=Double.parseDouble(ll[0]);
                            sample2[j][1]=Double.parseDouble(ll[1]);
                            Log.d("New X2:",String.valueOf(sample2[j][0]));
                            Log.d("New Y2:",String.valueOf(sample2[j][1]));
                            break;

                        case 2:
                            ll=ss.split(",");
                            sample3[j][0]=Double.parseDouble(ll[0]);
                            sample3[j][1]=Double.parseDouble(ll[1]);
                            Log.d("New X3:",String.valueOf(sample3[j][0]));
                            Log.d("New Y3:",String.valueOf(sample3[j][1]));
                            break;

                        case 3:
                            ll=ss.split(",");
                            sample4[j][0]=Double.parseDouble(ll[0]);
                            sample4[j][1]=Double.parseDouble(ll[1]);
                            break;

                        case 4:
                            ll=ss.split(",");
                            sample5[j][0]=Double.parseDouble(ll[0]);
                            sample5[j][1]=Double.parseDouble(ll[1]);
                            break;
                    }
                    j++;
                }
                else
                {
                    i++;
                    j=0;
                }


            }
            if(sampleNo>0)
            {
                double cc;
                switch(sampleNo)
                {
                    case 1:
                        cc=cosine(sample1,sample2);
                        s2.append(cc);
                        Log.d("cos sample1 & sample2:",String.valueOf(cc));

                        s2.append("\n************");
                        break;

                    case 2:
                        cc=cosine(sample1,sample3);
                        s2.append(cc);
                        Log.d("cos sample1 & sample3:",String.valueOf(cc));
                        s2.append(",");
                        cc=cosine(sample3,sample2);
                        s2.append(cc);
                        Log.d("cos sample2 & sample3:",String.valueOf(cc));
                        s2.append("\n************");
                        break;

                    case 3:
                        cc=cosine(sample4,sample2);
                        s2.append(cc);
                        Log.d("cos sample2 & sample4:",String.valueOf(cc));
                        s2.append(",");
                        cc=cosine(sample4,sample3);
                        s2.append(cc);
                        Log.d("cos sample3 & sample4:",String.valueOf(cc));
                        s2.append(",");
                        cc=cosine(sample1,sample4);
                        s2.append(cc);
                        Log.d("cos sample1 & sample4:",String.valueOf(cc));
                        s2.append("\n************");
                        break;

                    case 4:
                        cc=cosine(sample5,sample2);
                        s2.append(cc);
                        Log.d("cos sample2 & sample5:",String.valueOf(cc));
                        s2.append(",");
                        cc=cosine(sample5,sample3);
                        s2.append(cc);
                        Log.d("cos sample3 & sample5:",String.valueOf(cc));
                        s2.append(",");
                        cc=cosine(sample1,sample5);
                        s2.append(cc);
                        Log.d("cos sample1 & sample5:",String.valueOf(cc));
                        s2.append(",");
                        cc=cosine(sample4,sample5);
                        s2.append(cc);
                        Log.d("cos sample4 & sample5:",String.valueOf(cc));
                        s2.append("\n************");
                        break;
                }

                cosWrite(s2,"cos");
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    double cosine(double a[][],double b[][])
    {
        double c=0,z=0;
        int i=0;
        double ss=0,ss1=0,ss2=0,dotp=0;
        /*Mat A,B;
        double magnitude=0;
        A= new Mat(32,2, CvType.CV_64FC1);
        B= new Mat(32,2, CvType.CV_64FC1);
        while(i<32)
        {
            A.put(i,0,a[i][0]);
            A.put(i,1,a[i][1]);
            B.put(i,0,b[i][0]);
            B.put(i,1,b[i][1]);
            ss1+=(a[i][0]*a[i][0])+(a[i][1]*a[i][1]);
            ss2+=(b[i][0]*b[i][0])+(b[i][1]*b[i][1]);
        }
        magnitude=Math.sqrt((ss1+ss2));
        dotp=A.dot(B);
        c=dotp/magnitude;
        Log.d("Cos :",String.valueOf(c));*/

        //
        while(i<32)
        {
            ss=(a[i][0]*b[i][0])+(a[i][1]*b[i][1]);
            ss1=(a[i][0]*a[i][0])+(a[i][1]*a[i][1]);
            ss2=(b[i][0]*b[i][0])+(b[i][1]*b[i][1]);
           // Log.d("ss value:",String.valueOf(i)+","+String.valueOf(ss1)+","+String.valueOf(ss2)+","+String.valueOf(ss));
            //magnitude=Math.sqrt(ss1+ss2);
            ss1=Math.sqrt(ss1);
            ss2=Math.sqrt(ss2);
            c+=ss/(ss1*ss2);
            //c+=ss/(magnitude);

            i++;

        }
        c=c/32;


        z=Math.acos(c);
        //Log.d("Cos inverse:",String.valueOf(z));
        //Log.d("cos:",String.valueOf(c)+","+String.valueOf(ss1)+","+String.valueOf(ss2)+","+String.valueOf(ss));

        return c;

    }

    void cosWrite(StringBuilder sb,String fileName)
    {
        try {
            File dftfile = new File(Environment.getExternalStorageDirectory().toString()+"/"+fileName+".txt");

            if(!dftfile.exists())
            {
                dftfile.createNewFile();

            }
            FileWriter fw =new FileWriter(dftfile,true);
            //fw.append(text);
            //fw.append(text1);
            fw.append("\n=======");

            fw.append(sb);
            fw.flush();
            fw.close();
            Toast.makeText(context1, fileName+" calculated", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            //Toast.makeText(getBaseContext(), e.getMessage(),
            // Toast.LENGTH_SHORT).show();
        }

    }
}


